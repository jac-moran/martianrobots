package com.jacintomoran.martianrobots.map;

import com.jacintomoran.martianrobots.exception.MapSizeTooBigException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarsGridTest {

    @Test
    void validDimensionsCreateGrid() throws MapSizeTooBigException {
        MarsGrid grid = new MarsGrid(50, 50);
        assertEquals(50, grid.getMaxX());
        assertEquals(50, grid.getMaxY());
    }

    @Test
    void biggerDimensionsThrowMapSizeTooBigException() {
        assertThrows(MapSizeTooBigException.class, () -> new MarsGrid(51, 10));
        assertThrows(MapSizeTooBigException.class, () -> new MarsGrid(10, 51));
    }

    @Test
    void shouldValidateGridBoundaries() throws MapSizeTooBigException {
        MarsGrid grid = new MarsGrid(5, 3);

        // Corners and interior
        assertTrue(grid.isWithinBounds(new GridPoint(0, 0)));
        assertTrue(grid.isWithinBounds(new GridPoint(5, 3)));
        assertTrue(grid.isWithinBounds(new GridPoint(2, 1)));

        // Out of bounds
        assertFalse(grid.isWithinBounds(new GridPoint(-1, 0)));
        assertFalse(grid.isWithinBounds(new GridPoint(0, -1)));
        assertFalse(grid.isWithinBounds(new GridPoint(6, 3)));
        assertFalse(grid.isWithinBounds(new GridPoint(5, 4)));
    }

    @Test
    void scentShouldTrackPointAndDirection() throws MapSizeTooBigException {
        MarsGrid grid = new MarsGrid(5, 5);
        GridPoint corner = new GridPoint(5, 5);

        assertFalse(grid.hasScent(corner, GridDirection.NORTH));
        assertFalse(grid.hasScent(corner, GridDirection.EAST));

        grid.addStepToScents(corner, GridDirection.NORTH);

        assertTrue(grid.hasScent(corner, GridDirection.NORTH));
        assertFalse(grid.hasScent(corner, GridDirection.EAST)); // Different edge at same corner
        assertFalse(grid.hasScent(new GridPoint(4, 5), GridDirection.NORTH)); // Different point
    }
}
