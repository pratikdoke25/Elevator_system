package org.example.elevators.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    @Test
    void testMove() {
        Elevator elevator = new Elevator(0, 5); //default current floor is 0
        elevator.setTargetFloor(2);
        elevator.move();
        assertEquals(1, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
        elevator.setTargetFloor(1);
        elevator.move();
        assertEquals(1, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(1, elevator.getCurrentFloor());
    }

    @Test
    void testLetPeopleLeave() {
        Elevator elevator = new Elevator(0, 3);
        assertThrows(IllegalArgumentException.class, () -> elevator.setTargetFloor(10));
        elevator.setTargetFloor(2);
        elevator.move();
        elevator.move();
        Person person1 = new Person(5);
        Person person2 = new Person(3);
        Person person3 = new Person(2);
        try {
            Method addPersonMethod = Elevator.class.getDeclaredMethod("addPerson", Person.class);
            addPersonMethod.setAccessible(true);
            addPersonMethod.invoke(elevator, person1);
            addPersonMethod.invoke(elevator, person2);
            addPersonMethod.invoke(elevator, person3);
        } catch (NoSuchMethodException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }

        elevator.letPeopleLeave();

        assertEquals(2, elevator.getPeople().size());
        assertTrue(elevator.getPeople().contains(person1));
        assertTrue(elevator.getPeople().contains(person2));
        assertFalse(elevator.getPeople().contains(person3));
    }

    @Test
    void testUpdateTargetFloor() {
        Elevator elevator = new Elevator(0, 5);
        assertNull(elevator.getTargetFloor());

        Person person1 = new Person(2);
        Person person2 = new Person(3);
        Person person3 = new Person(4);

        elevator.addPerson(person1);
        elevator.addPerson(person2);
        elevator.addPerson(person3);

        elevator.updateTargetFloor();
        assertEquals(4, elevator.getTargetFloor());

        elevator.move();
        elevator.move();
        elevator.letPeopleLeave();
        assertEquals(4, elevator.getTargetFloor());


    }
}