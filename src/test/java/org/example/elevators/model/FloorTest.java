package org.example.elevators.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {
    private Floor floor;

    @BeforeEach
    void setUp() {
        floor = new Floor(1);
    }


    @Test
    void testAddPerson() {
        Person person1 = new Person(2);
        Person person2 = new Person(0);

        floor.addPerson(person1);
        assertTrue(floor.getWaitingUp().contains(person1));
        assertFalse(floor.getWaitingDown().contains(person1));

        floor.addPerson(person2);
        assertTrue(floor.getWaitingUp().contains(person1));
        assertTrue(floor.getWaitingDown().contains(person2));
    }

    @Test
    void testLetPeopleEnterElevator() {
        Elevator elevator = new Elevator(0, 5);
        elevator.setTargetFloor(1);
        elevator.move();
        floor.clickButton(Direction.UP);
        floor.addPerson(new Person(2));
        floor.addPerson(new Person(3));

        floor.letPeopleEnterElevator(elevator);

        assertEquals(2, elevator.getPeople().size());
        assertFalse(floor.hasPeopleWaiting());
    }

    @Test
    void testHasPeopleWaiting() {
        assertFalse(floor.hasPeopleWaiting());

        floor.addPerson(new Person(2));
        assertTrue(floor.hasPeopleWaiting());
    }

    void testGetWaitingPeople() {
        Person person1 = new Person(2);
        Person person2 = new Person(3);
        floor.addPerson(person1);
        floor.addPerson(person2);

        List<Person> waitingPeople = floor.getWaitingPeople();
        assertEquals(2, waitingPeople.size());
        assertTrue(waitingPeople.contains(person1));
        assertTrue(waitingPeople.contains(person2));
    }
}