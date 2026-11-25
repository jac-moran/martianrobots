package com.jacintomoran.martianrobots.map;

import java.util.Arrays;

/**
 * Defines the orientation (direction) that a robot can have on the grid.
 */
public enum GridDirection {
    NORTH('N', 0, 1),
    EAST('E', 1, 0),
    SOUTH('S', 0, -1),
    WEST('W', -1, 0);

    private final char symbol;
    private final int dX;
    private final int dY;

    GridDirection(char symbol, int deltaX, int deltaY) {
        this.symbol = symbol;
        this.dX = deltaX;
        this.dY = deltaY;
    }

    /**
     * Obtains a Direction from a character in the config
     * @param c can be N, S, E or W
     * @return
     */
    public static GridDirection fromSymbol(char c) {
        return Arrays.stream(values())
                .filter(v -> v.symbol == c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown direction symbol: " + c));
    }

    /**
     * Turns the robot to its right
     * @return
     */
    public GridDirection right() {
        GridDirection[] all = values();
        return all[(ordinal() + 1) % all.length];
    }

    /**
     * Turns the robot to its left
     * @return
     */
    public GridDirection left() {
        GridDirection[] all = values();
        return all[(ordinal() - 1 + all.length) % all.length];
    }

    /**
     * Obtains the GridPoint to which this moving forward on this direction will take.
     * @param base
     * @return
     */
    public GridPoint getMovedPoint(GridPoint base) {
        GridPoint p = new GridPoint(base.getX(), base.getY());
        p.addX(this.dX);
        p.addY(this.dY);
        return p;
    }

    /**
     *
     * @return String representation
     */
    public String toString() {
        return Character.toString(symbol);
    }
}