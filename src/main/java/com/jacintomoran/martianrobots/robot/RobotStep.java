package com.jacintomoran.martianrobots.robot;

import java.util.Arrays;

public enum RobotStep {
    FORWARD('F'),
    LEFT('L'),
    RIGHT('R');

    private final char symbol;

    RobotStep(char symbol) {
        this.symbol = symbol;
    }

    public static boolean isSymbol(int c) {
        return Arrays.stream(values()).anyMatch((v) -> c == v.symbol);
    }

    public static RobotStep fromSymbol(char c) {
        return Arrays.stream(values())
                .filter(v -> v.symbol == c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown robot step symbol: " + c));
    }

    public String toString() {
        return Character.toString(symbol);
    }
}
