package com.jacintomoran.martianrobots.input;

import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.exception.InvalidNumberOfInputLinesException;
import com.jacintomoran.martianrobots.exception.MapSizeTooBigException;
import com.jacintomoran.martianrobots.map.GridDirection;
import com.jacintomoran.martianrobots.map.GridPoint;
import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputProcessorTest {

    private InputStream toStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void shouldInitialiseAll() throws IOException, InputException {
        String input = """
                5 3
                1 1 E
                RFRFRFRF
                3 2 N
                FRRFLLFFRRFLL
                """;

        InputProcessor processor = new InputProcessor(toStream(input));
        MarsGrid grid = processor.initGrid();
        List<Robot> robots = processor.initRobots();

        assertEquals(5, grid.getMaxX());
        assertEquals(3, grid.getMaxY());
        assertEquals(2, robots.size());

        assertEquals(new GridPoint(1, 1), robots.get(0).getPoint());
        assertEquals(GridDirection.EAST, robots.get(0).getDirection());

        assertEquals(new GridPoint(3, 2), robots.get(1).getPoint());
        assertEquals(GridDirection.NORTH, robots.get(1).getDirection());
    }

    @Test
    void inputWithoutRobotsSucceeds() throws IOException, InputException {
        InputProcessor processor = new InputProcessor(toStream("10 20\n"));
        MarsGrid grid = processor.initGrid();
        List<Robot> robots = processor.initRobots();

        assertEquals(10, grid.getMaxX());
        assertEquals(20, grid.getMaxY());
        assertTrue(robots.isEmpty());
    }

    @Test
    void evenLineNumberShouldThrowInvalidNumberOfInputLinesException() {
        String invalidLinesInput = """
                5 3
                1 1 E
                """;
        assertThrows(InvalidNumberOfInputLinesException.class,
                () -> new InputProcessor(toStream(invalidLinesInput)));
    }

    @Test
    void tooLongInputShouldThrowInputException() {
        String longInputLine = "F".repeat(101);
        assertThrows(InputException.class,
                () -> new InputProcessor(toStream(longInputLine)));
    }

    @Test
    void wrongFormatShouldThrowInputException() throws IOException, InputException {
        InputProcessor wrongItemCount = new InputProcessor(toStream("5\n"));
        assertThrows(InputException.class, wrongItemCount::initGrid);

        InputProcessor nonNumeric = new InputProcessor(toStream("5 A\n"));
        assertThrows(InputException.class, nonNumeric::initGrid);
    }

    @Test
    void biggerMapShouldThrowMapSizeTooBigException() throws IOException, InputException {
        InputProcessor tooBig = new InputProcessor(toStream("51 10\n"));
        assertThrows(MapSizeTooBigException.class, tooBig::initGrid);
        InputProcessor tooBigY = new InputProcessor(toStream("49 54\n"));
        assertThrows(MapSizeTooBigException.class, tooBigY::initGrid);
    }

    @Test
    void wrongRobotsConfigShouldThrowInputException() throws IOException, InputException {
        // Missing orientation
        InputProcessor missingField = new InputProcessor(toStream("5 3\n1 1\nFF\n"));
        assertThrows(InputException.class, missingField::initRobots);

        // Non-numeric coordinate
        InputProcessor badCoords = new InputProcessor(toStream("5 3\n1 X N\nFF\n"));
        assertThrows(InputException.class, badCoords::initRobots);

        // Invalid orientation
        InputProcessor badDir = new InputProcessor(toStream("5 3\n1 1 Z\nFF\n"));
        assertThrows(InputException.class, badDir::initRobots);

        // Invalid step character
        InputProcessor badPath = new InputProcessor(toStream("5 3\n1 1 N\nFXR\n"));
        assertThrows(InputException.class, badPath::initRobots);
    }
}
