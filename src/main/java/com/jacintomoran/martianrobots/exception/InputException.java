package com.jacintomoran.martianrobots.exception;

public class InputException extends Throwable {

    public InputException(int linenumber, String message) {
        super(message + "\n   at input line " + linenumber);
    }
}
