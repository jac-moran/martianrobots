package com.jacintomoran.martianrobots.map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GridDirectionTest {

    @ParameterizedTest
    @CsvSource({
            "N, NORTH",
            "E, EAST",
            "S, SOUTH",
            "W, WEST"
    })
    void validSymbolShouldReturnDirection(char symbol, GridDirection expected) {
        assertEquals(expected, GridDirection.fromSymbol(symbol));
    }

    @Test
    void wrongSymbolShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> GridDirection.fromSymbol('X'));
        assertThrows(IllegalArgumentException.class, () -> GridDirection.fromSymbol('n'));
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, EAST",
            "EAST, SOUTH",
            "SOUTH, WEST",
            "WEST, NORTH"
    })
    void rightShouldRotateClockwise(GridDirection initial, GridDirection expected) {
        assertEquals(expected, initial.right());
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, WEST",
            "WEST, SOUTH",
            "SOUTH, EAST",
            "EAST, NORTH"
    })
    void leftShouldRotateCounterClockwise(GridDirection initial, GridDirection expected) {
        assertEquals(expected, initial.left());
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, 2, 2, 2, 3",
            "EAST,  2, 2, 3, 2",
            "SOUTH, 2, 2, 2, 1",
            "WEST,  2, 2, 1, 2"
    })
    void getMovedPointShouldNotAlterOriginal(
            GridDirection direction, int startX, int startY, int expectedX, int expectedY) {
        GridPoint start = new GridPoint(startX, startY);
        GridPoint moved = direction.getMovedPoint(start);

        assertEquals(new GridPoint(expectedX, expectedY), moved);
        assertEquals(new GridPoint(startX, startY), start); // Original remains unchanged
        assertNotEquals(moved, start);
    }

    @Test
    void toStringReturnsSingleCharSymbol() {
        assertEquals("N", GridDirection.NORTH.toString());
        assertEquals("E", GridDirection.EAST.toString());
        assertEquals("S", GridDirection.SOUTH.toString());
        assertEquals("W", GridDirection.WEST.toString());
    }
}
