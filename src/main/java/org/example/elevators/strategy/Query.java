package org.example.elevators.strategy;

import org.example.elevators.model.Direction;

public class Query {
    private int waitingUp;
    private int waitingDown;
    private final int floor;

    public Query(int waitingUp, int waitingDown, int floor) {
        this.waitingUp = waitingUp;
        this.waitingDown = waitingDown;
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void untag(Direction direction) {
        if (direction == Direction.UP)
            untagUp();
        else
            untagDown();
    }

    private void untagUp() {
        waitingUp = 0;
    }

    private void untagDown() {
        waitingDown = 0;
    }

    public void untagAll() {
        untagUp();
        untagDown();
    }

    public boolean getWaitingUp() {
        return waitingUp > 0;
    }

    public boolean getWaitingDown() {
        return waitingDown > 0;
    }

    public boolean tagged() {
        return getWaitingDown() || getWaitingUp();
    }
}
