package org.example.elevators.strategy;

import org.example.elevators.model.Direction;

public class Query {
    private boolean up;
    private boolean down;
    private final int floor;

    public Query(boolean up, boolean down, int floor) {
        this.up = up;
        this.down = down;
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void untag(Direction direction) {
        if (direction == Direction.UP)
            up = false;
        else
            down = false;
    }
    public void untagAll() {
        up = false;
        down = false;
    }
    public boolean isUp() {
        return up;
    }
    public boolean isDown() {
        return down;
    }
    public boolean tagged() {
        return up || down;
    }
}
