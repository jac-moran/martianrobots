package com.jacintomoran.martianrobots.robot;

import com.jacintomoran.martianrobots.logger.Logger;
import com.jacintomoran.martianrobots.map.GridDirection;
import com.jacintomoran.martianrobots.map.GridPoint;
import com.jacintomoran.martianrobots.map.MarsGrid;

import java.util.HashSet;
import java.util.Set;

public class Robot {

    private GridPoint point;
    private GridDirection direction;
    private String path;
    private int nextStepIndex;
    private boolean isLost;

    private Set<Logger> loggers;

    /**
     * Constructs a Robot object
     * @param point Initial position in the grid
     * @param direction Initial orientation
     * @param path the list of steps the robot will follow
     */
    public Robot(GridPoint point,
                 GridDirection direction,
                 String path) {
        this.point =  point;
        this.direction = direction;
        this.path = path;
        this.nextStepIndex = 0;
        this.isLost = false;
        this.loggers = new HashSet<>();
    }

    /**
     * Adds a logger to the Robot object, which will be notified of the events occurred
     * @param logger logger to be used
     */
    public void addLogger(Logger logger) {
        this.loggers.add(logger);
    }

    /**
     *
     * @return String representation of the Robot object
     */
    public String toString() {
        return getPoint().toString()
                + " "
                + getDirection().toString()
                + (isLost? " LOST": "");
    }

    /**
     *
     * @return the location - grid point where the robot is now
     */
    public GridPoint getPoint() {
        return point;
    }

    /**
     *
     * @return the orientation, where the robot is pointing to now
     */
    public GridDirection getDirection() {
        return direction;
    }

    /**
     * Marks the robot as lost. This will stop further steps from being taken.
     */
    public void lose() {
        isLost = true;
    }

    /**
     *
     * @return whether the robot is lost.
     */
    public boolean isLost() {
        return isLost;
    }

    /**
     *
     * @return the next step the robot will take
     */
    public RobotStep getNextStep() {
        return RobotStep.fromSymbol(path.charAt(nextStepIndex));
    }

    /**
     * Performs the next step, as obtained from getNextStep().<br/>
     * Once the step has been successfully completed, the step pointer will increase
     * @param grid The grid on which steps are being taken.
     * @return
     */
    public boolean doNextStep(MarsGrid grid) {
        for (Logger log : loggers) {
            log.showRobot(this);
        }
        RobotStep next = getNextStep();
        switch (next) {
            case RobotStep.RIGHT -> this.direction = this.direction.right();
            case RobotStep.LEFT -> this.direction = this.direction.left();
            case RobotStep.FORWARD -> {
                // if there is a scent for the edge we are crossing, ignore this movement
                if (!grid.hasScent(this.point, this.direction)) {
                    GridPoint nextPoint = this.direction.getMovedPoint(this.point);
                    if (grid.isWithinBounds(nextPoint)) {
                        // next location is within the grid, go there
                        this.point = nextPoint;
                    } else {
                        // out of bounds, robot is lost and scent is added on the edge
                        this.lose();
                        grid.addStepToScents(this.point, this.direction);
                        for (Logger log : loggers) {
                            log.scentSet(this);
                        }
                    }
                } else {
                    for (Logger log : loggers) {
                        log.ignoredStepForScent(this);
                    }
                }
            }
        }
        nextStepIndex++;
        return nextStepIndex < path.length();
    }
}
