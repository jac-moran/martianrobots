package com.jacintomoran.martianrobots.map;

import java.util.Objects;

/**
 * Represents a position in the grid
 */
public class GridPoint {

    int x;
    int y;

    /**
     * Constructs a position with its coordinates
     * @param x X (horizontal) coordinate
     * @param y Y (vertical) coordinate
     */
    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Adds to the X coordinate (used in movements)
     * @param delta increase to the X coordinate
     */
    public void addX(int delta) {
        x += delta;
    }

    /**
     * Adds to the Y coordinate (used in movements)
     * @param delta increase to the Y coordinate
     */
    public void addY(int delta) {
        y += delta;
    }

    /**
     *
     * @return String representation
     */
    public String toString() {
        return "" + x + " " + y;
    }

    /**
     * Used to identify a point object as a collection key
     * @param o   the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GridPoint gridPoint = (GridPoint) o;
        return x == gridPoint.x && y == gridPoint.y;
    }

    /**
     * Used to identify a point object as a collection key
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
