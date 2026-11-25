package com.jacintomoran.martianrobots.robot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotStepTest {

    @ParameterizedTest
    @CsvSource({
            "F, FORWARD",
            "L, LEFT",
            "R, RIGHT"
    })
    void validSymbolReturnsStep(char symbol, RobotStep expected) {
        assertEquals(expected, RobotStep.fromSymbol(symbol));
    }

    @Test
    void invalidSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> RobotStep.fromSymbol('B'));
        assertThrows(IllegalArgumentException.class, () -> RobotStep.fromSymbol('f'));
    }

    @Test
    void isSymbolTest() {
        assertTrue(RobotStep.isSymbol('F'));
        assertTrue(RobotStep.isSymbol('L'));
        assertTrue(RobotStep.isSymbol('R'));

        assertFalse(RobotStep.isSymbol('X'));
        assertFalse(RobotStep.isSymbol('f'));
        assertFalse(RobotStep.isSymbol(' '));
    }

    @Test
    void toStringTest() {
        assertEquals("F", RobotStep.FORWARD.toString());
        assertEquals("L", RobotStep.LEFT.toString());
        assertEquals("R", RobotStep.RIGHT.toString());
    }
}
