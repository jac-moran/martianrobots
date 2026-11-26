package com.jacintomoran.martianrobots;

import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.input.InputProcessor;
import com.jacintomoran.martianrobots.map.MarsGrid;
import com.jacintomoran.martianrobots.robot.Robot;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnerTest {

    @Test
    void testSampleOutput() throws IOException, InputException {
        testOutput("sample0.txt", "sample0_output.txt");
    }

    @Test
    void testAdditionalOutput() throws IOException, InputException {
        testOutput("sample1.txt", "sample1_output.txt");
        testOutput("sample2.txt", "sample2_output.txt");
        testOutput("sample3.txt", "sample3_output.txt");
    }

    private void testOutput(String inputFile, String expectedFile) throws IOException, InputException {
        Runner r = new Runner(toStream(inputFile));
        String output = r.run();
        String expected = readExpectedOutput(expectedFile);
        assertEquals(expected, output);
    }

    private InputStream toStream(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private String readExpectedOutput(String filename) throws IOException {
        return new String(toStream(filename).readAllBytes(), StandardCharsets.UTF_8);
    }
}
