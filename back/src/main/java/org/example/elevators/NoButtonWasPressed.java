package org.example.elevators;

public class NoButtonWasPressed extends RuntimeException {
    public NoButtonWasPressed() {
        super("No button was pressed");
    }
}
