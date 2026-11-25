package com.jacintomoran.martianrobots;

import com.jacintomoran.martianrobots.exception.InputException;
import com.jacintomoran.martianrobots.logger.StdOutLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {

    /**
     * Convenience main method to run from the command line.<br/>
     * Usage:  java -classpath martianrobots-1.0-SNAPSHOT.jar com.jacintomoran.martianrobots.Main
     * [--gui-log] [--stdout-log] [data filename]
     * @param args command line arguments
     * @throws IOException
     * @throws InputException
     */
    public static void main(String[] args) throws IOException, InputException {
        List<String> argList = List.of(args);
        boolean stdoutLog = argList.contains("--stdout-log");

        // everything that isn't one of our flags goes on to Runner (the input file, if any)
        String[] runnerArgs = argList.stream()
                .filter(a -> !a.equals("--stdout-log"))
                .toArray(String[]::new);

        InputStream fis = null;
        if (runnerArgs.length > 0) {
            fis = new FileInputStream(runnerArgs[0]);
        } else {
            fis = System.in;
        }
        Runner runner = new Runner(fis);
        if (stdoutLog) {
            runner.addLogger(new StdOutLogger());
        }

        String output = runner.run();
        System.out.println("\n" + output);
    }


}
