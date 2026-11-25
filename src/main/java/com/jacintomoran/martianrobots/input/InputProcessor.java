package com.jacintomoran.martianrobots.input;

import com.jacintomoran.martianrobots.robot.Robot;
import com.jacintomoran.martianrobots.robot.RobotStep;
import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.exception.InvalidNumberOfInputLinesException;
import com.jacintomoran.martianrobots.map.GridDirection;
import com.jacintomoran.martianrobots.map.GridPoint;
import com.jacintomoran.martianrobots.map.MarsGrid;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InputProcessor {

    private static final int MAX_INPUT_CHARACTERS = 100;

    final List<String> lines;

    /**
     * Constructs the InputProcessor object, performing an initial input data validation.<br/>
     * initGrid() and initRobots() need to be called as well to complete the input processing.
     * @param input all input data
     * @throws IOException for any errors with the InpjutStream
     * @throws InputException for any configuration-specific errors
     */
    public InputProcessor(InputStream input) throws IOException, InputException {
        String content = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        // input will be less than 100 characters in length
        // check is done so that line feed characters are not counted
        if (content.lines().collect(Collectors.joining()).length() > MAX_INPUT_CHARACTERS) {
            throw new InputException(0, "input should never be longer than " + MAX_INPUT_CHARACTERS + " characters");
        }
        lines = content.lines().toList();
        // input must contain the initial size line, plus 2 lines per robot: always an odd number
        // intentionally allowing an input with size with no robots: 1 line
        if (lines.size() % 2 != 1) {
            throw new InvalidNumberOfInputLinesException(lines.size());
        }
    }

    /**
     * Inits the Mars grid, using the first line of the input as loaded in the constructor.
     * @return The grid to be used in the run, with the configured size.
     * @throws InputException if configuration is not correct
     */
    public MarsGrid initGrid() throws InputException {
        String[] items = lines.getFirst().split(" ");
        if (items.length != 2) {
            throw new InputException(1, "first line should contain 2 items, but has " + items.length);
        }
        try {
            int sizeX = Integer.parseInt(items[0]);
            int sizeY = Integer.parseInt(items[1]);
            return new MarsGrid(sizeX, sizeY);
        } catch(NumberFormatException e) {
            throw new InputException(1, "non-valid first line: should contain two int numbers");
        }
    }

    /**
     * Initialises and returns the robots (input lines by pairs, starting from line #2)
     * @return robots created as per the config data loaded in the constructor
     * @throws InputException
     */
    public List<Robot> initRobots() throws InputException {
        // 2 lines per robot, so validating lines from second, two by two
        List<Robot> robots = new ArrayList<>();
        for (int i=1; i<lines.size(); i+=2) {
            Robot r = validateAndInitRobot(i, lines.get(i), lines.get(i+1));
            robots.add(r);
        }
        return robots;
    }

    /**
     * validates the two robot lines for a specific robot: initial position plus direction, and path
     * @param initial content of the first input line
     * @param path content of the second input line
     */
    Robot validateAndInitRobot(int linenumber, String initial, String path) throws InputException {
        // first line
        String[] items = initial.split(" ");
        if(items.length != 3) {
            throw new InputException(linenumber, "Robot initialisation line should contain position and direction");
        }
        GridPoint location = null;
        try {
            location = new GridPoint(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
        } catch (NumberFormatException e) {
            throw new InputException(linenumber, "Robot position should be integer numbers");
        }
        GridDirection orientation = null;
        try {
            orientation = GridDirection.fromSymbol(items[2].charAt(0));
        } catch (IllegalArgumentException e) {
            throw new InputException(linenumber, "Robot direction must be one of N, S, E, W");
        }
        // second line
        if (!path.chars().allMatch(RobotStep::isSymbol)) {
            throw new InputException(linenumber + 1, "all robot movements should be F, L or R");
        }
        return new Robot(location, orientation, path);
    }
}
