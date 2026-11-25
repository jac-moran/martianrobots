package com.jacintomoran.martianrobots.logger;

import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;

public class StdOutLogger implements Logger {

    private MarsGrid grid;

    @Override
    public void setGrid(MarsGrid g) {
        grid = g;
    }

    @Override
    public void showNewRobot(Robot r) {
        System.out.println("\n*** ");
    }

    @Override
    public void showRobot(Robot r) {
        System.out.println(" - "
                + r.toString()
                + " ... " + r.getNextStep());
    }

    @Override
    public void showEndRobot(Robot r) {
        System.out.println(" O "
                + r.toString());
    }

    @Override
    public void scentSet(Robot r) {
        System.out.println(" ! scent set at " + r.getPoint() + " facing " + r.getDirection());
    }

    @Override
    public void ignoredStepForScent(Robot r) {
        System.out.println(" = movement ignored - scent");
    }

    @Override
    public void close() {
    }
}
