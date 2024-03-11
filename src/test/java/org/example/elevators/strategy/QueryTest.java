package org.example.elevators.strategy;

import org.example.elevators.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {
    private Query query;

    @BeforeEach
    void setUp() {
        query = new Query(2, 3, 5);
    }

    @Test
    void testGetFloor() {
        assertEquals(5, query.getFloor());
    }

    @Test
    void testUntagUp() {
        query.untag(Direction.UP);
        assertFalse(query.getWaitingUp());
    }

    @Test
    void testUntagDown() {
        query.untag(Direction.DOWN);
        assertFalse(query.getWaitingDown());
    }

    @Test
    void testUntagAll() {
        query.untagAll();
        assertFalse(query.getWaitingUp());
        assertFalse(query.getWaitingDown());
    }

    @Test
    void testGetWaitingUp() {
        assertTrue(query.getWaitingUp());
    }

    @Test
    void testGetWaitingDown() {
        assertTrue(query.getWaitingDown());
    }

    @Test
    void testTagged() {
        assertTrue(query.tagged());
    }
}