package com.jacintomoran.martianrobots.map;

import com.jacintomoran.martianrobots.exception.MapSizeTooBigException;

import java.util.HashSet;
import java.util.Set;

/**
 * The martian grid where robots move about
 */
public class MarsGrid {

    private final int MAX_COORDINATE = 50;

    private final int maxX;
    private final int maxY;

    private Set<GridScent> scents;

    /**
     * Constructs a grid with the maximum X and Y values
     * @param x grid will extend horizontally from 0 to x
     * @param y grid will extend vertically from 0 to y
     * @throws MapSizeTooBigException if any of the maximums is biogger than 50
     */
    public MarsGrid(int x, int y) throws MapSizeTooBigException {
        if (x > MAX_COORDINATE || y > MAX_COORDINATE) {
            throw new MapSizeTooBigException("maximum map dimensions cannot be bigger than "
                + MAX_COORDINATE);
        }
        maxX = x;
        maxY = y;
        scents = new HashSet<>();
    }

    /**
     *
     * @return the maximum of the X coordinate
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     *
     * @return the maximum of the Y coordinate
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Registers an edge in the scents when a robot falls through it
     * @param p grid point the edge is adjacent to
     * @param d direction of the edge (corner points actually have two edges)
     */
    public void addStepToScents(GridPoint p, GridDirection d) {
        scents.add(new GridScent(p, d));
    }

    /**
     *
     * @param p point where to check for a scent
     * @param d direction of the edge on which to check an scent
     * @return whether there is a scent there
     */
    public boolean hasScent(GridPoint p, GridDirection d) {
        return scents.contains(new GridScent(p, d));
    }

    /**
     *
     * @param p point to check
     * @return whether the point is within bounds in the grid or has gone off it
     */
    public boolean isWithinBounds(GridPoint p) {
        return (p.getX() >= 0
                && p.getY() >= 0
                && p.getX() <= maxX
                && p.getY() <= maxY);
    }

    private record GridScent(GridPoint point, GridDirection direction) {}
}
