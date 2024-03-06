package org.example.elevators.model;

public enum Direction {
    UP(1), DOWN(-1), NONE(0);
    private final int value;

    Direction(int value) {
        this.value = value;
    }

}
