package org.example.elevators.model;

public enum Direction {
    UP(1), DOWN(-1), NONE(0);
    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction fromInt(int currentFloor, int targetFloor) {
        if (currentFloor < targetFloor) {
            return UP;
        } else if (currentFloor > targetFloor) {
            return DOWN;
        } else {
            return NONE;
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return switch (this) {
            case UP -> "UP";
            case DOWN -> "DOWN";
            case NONE -> "NONE";
        };
    }
}
