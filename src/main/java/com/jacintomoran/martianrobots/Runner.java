package com.jacintomoran.martianrobots;

import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.input.InputProcessor;
import com.jacintomoran.martianrobots.logger.Logger;
import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class made to ingest and validate all the inputs and run the data.
 */
public class Runner {

    private final MarsGrid grid;
    private final List<Robot> robots;

    private final Set<Logger> loggers;

    /**
     * Constructs a Runner object, obtaining input data from an InputStream
     * @param fis
     * @throws IOException for any errors with the InpjutStream
     * @throws InputException for any configuration-specific errors
     */
    public Runner(InputStream fis) throws IOException, InputException {
        loggers = new HashSet<>();
        InputProcessor ip = new InputProcessor(fis);
        grid = ip.initGrid();
        robots = ip.initRobots();
    }

    /**
     * Adds a logger to the run. An unlimited number of these can be added.
     * @param logger
     */
    public void addLogger(Logger logger) {
        logger.setGrid(grid);
        loggers.add(logger);
        robots.forEach(r -> r.addLogger(logger));
    }

    /**
     * Runs the configuration as loaded. Logs will be pushed to the loggers added,
     * and the formal output is returned as single String.
     * @return output as described in the documentation
     */
    public String run() {
        try {
            StringBuilder output = new StringBuilder();

            for (Robot robot : robots) {
                for (Logger log : loggers) {
                    log.showNewRobot(robot);
                }

                boolean doNext = true;
                do {
                    doNext = robot.doNextStep(grid);
                } while (!robot.isLost() && doNext);

                for (Logger log : loggers) {
                    log.showEndRobot(robot);
                }
                output.append(robot.toString()).append(System.lineSeparator());
            }

            return output.toString();
        } finally {
            // always close loggers, whatever happens
            for (Logger log : loggers) {
                log.close();
            }
        }
    }
}
