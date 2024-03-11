package org.example.elevators.model;

public class NoButtonWasPressed extends RuntimeException {
    public NoButtonWasPressed() {
        super("No button was pressed");
    }
}
