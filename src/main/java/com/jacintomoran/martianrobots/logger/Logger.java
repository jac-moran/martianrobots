package com.jacintomoran.martianrobots.logger;

import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;

public interface Logger {

    public void setGrid(MarsGrid g);
    public void showNewRobot(Robot r);
    public void showRobot(Robot r);
    public void showEndRobot(Robot r);
    public void scentSet(Robot r);
    public void ignoredStepForScent(Robot r);
    public void close();
}
