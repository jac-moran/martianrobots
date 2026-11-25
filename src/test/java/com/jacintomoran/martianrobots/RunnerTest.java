package com.jacintomoran.martianrobots;

import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.input.InputProcessor;
import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnerTest {

    private InputStream toStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void testSampleOutput() throws IOException, InputException {
        String input = """
                5 3
                1 1 E
                RFRFRFRF
                3 2 N
                FRRFLLFFRRFLL
                0 3 W
                LLFFFLFLFL
                """;
        String expected = """
                
                1 1 E
                3 3 N LOST
                2 3 S""";
        testOutput(input, expected);
    }

    @Test
    void testAdditionalOutput() throws IOException, InputException {
        String input = """
                20 20
                0 0 E
                FRF
                1 0 E
                FRF
                2 0 E
                FRF
                3 0 E
                FRF
                0 0 E
                FRFLFRFLFRFLFRFLFRF
                20 17 N
                FFFF
                20 17 N
                FFFFRF
                20 17 N
                FFFFRFRF
                """;
        String expected = """
                
                1 0 S LOST
                2 0 S LOST
                3 0 S LOST
                4 0 S LOST
                5 0 S LOST
                20 20 N LOST
                20 20 E LOST
                20 19 S""";
        testOutput(input, expected);
    }

    private void testOutput(String input, String expected) throws IOException, InputException {
        Runner r = new Runner(toStream(input));
        String output = r.run();
        assertEquals(expected, output);
    }
}
