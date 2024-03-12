package org.example.elevators.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {
    @Test
    void testFromIntWhenCurrentFloorIsLessThanTargetFloor() {
        Direction direction = Direction.fromInt(0, 5);
        assertEquals(Direction.UP, direction);
    }

    @Test
    void testFromIntWhenCurrentFloorIsGreaterThanTargetFloor() {
        Direction direction = Direction.fromInt(5, 0);
        assertEquals(Direction.DOWN, direction);
    }

    @Test
    void testFromIntWhenCurrentFloorIsEqualToTargetFloor() {
        Direction direction = Direction.fromInt(3, 3);
        assertEquals(Direction.NONE, direction);
    }
}