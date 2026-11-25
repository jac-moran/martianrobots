package com.jacintomoran.martianrobots.robot;

import com.jacintomoran.martianrobots.exception.MapSizeTooBigException;
import com.jacintomoran.martianrobots.map.GridDirection;
import com.jacintomoran.martianrobots.map.GridPoint;
import com.jacintomoran.martianrobots.map.MarsGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Robot")
class RobotTest {

    private MarsGrid grid;

    @BeforeEach
    void setUp() throws MapSizeTooBigException {
        grid = new MarsGrid(5, 3);
    }

    @Nested
    @DisplayName("When moving within grid bounds")
    class WithinBoundsTests {

        @Test
        @DisplayName("completes a closed loop and returns to start")
        void completesClosedLoop() {
            Robot robot = new Robot(new GridPoint(1, 1), GridDirection.EAST, "RFRFRFRF");

            while (robot.doNextStep(grid)) {}

            assertEquals("1 1 E", robot.toString());
            assertFalse(robot.isLost());
        }
    }

    @Nested
    @DisplayName("When encountering grid edges and scents")
    class EdgeAndScentTests {

        @Test
        @DisplayName("becomes LOST and leaves a scent when stepping off the grid")
        void fallsOffGrid() {
            Robot robot = new Robot(new GridPoint(3, 2), GridDirection.NORTH, "FRRFLLFFRRFLL");

            boolean hasMoreSteps = true;
            while (hasMoreSteps && !robot.isLost()) {
                hasMoreSteps = robot.doNextStep(grid);
            }

            assertTrue(robot.isLost());
            assertEquals("3 3 N LOST", robot.toString());
            assertTrue(grid.hasScent(new GridPoint(3, 3), GridDirection.NORTH));
        }

        @Test
        @DisplayName("ignores fatal forward step when a scent is present")
        void ignoresFatalStepWithScent() {
            grid.addStepToScents(new GridPoint(3, 3), GridDirection.NORTH);
            Robot robot = new Robot(new GridPoint(0, 3), GridDirection.WEST, "LLFFFLFLFL");

            while (robot.doNextStep(grid)) {}

            assertFalse(robot.isLost());
            assertEquals("2 3 S", robot.toString());
        }
    }
}
