package com.jacintomoran.martianrobots.exception;

public class InvalidNumberOfInputLinesException extends InputException {

    public InvalidNumberOfInputLinesException(int linenumber) {
        super(linenumber, "Invalid number of lines in input");
    }
}
