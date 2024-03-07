package org.example.elevators.model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {
    private final int id;
    private final int floors;
    private int currentFloor;
    private Integer targetFloor;
    private final SortedSet<Person> people;

    public Elevator(int id, int floors) {
        this.id = id;
        this.floors = floors;
        this.currentFloor = 0;
        this.targetFloor = null;
        people = new TreeSet<>((p1, p2) -> {
            int d1 = Math.abs(p1.targetFloor() - currentFloor);
            int d2 = Math.abs(p2.targetFloor() - currentFloor);
            return d1 - d2;
        });
    }

    public void move() {
        switch (getDirection()) {
            case UP -> moveUp();
            case DOWN -> moveDown();
        }
    }

    public void letPeopleLeave() {
        people.removeIf(person -> person.targetFloor() == currentFloor);
    }

    void addPerson(Person person) {
        people.add(person);
    }

    void addPerson(List<Person> people) {
        this.people.addAll(people);
    }

    void moveUp() {
        currentFloor = Math.min(floors - 1, currentFloor + 1);
    }

    void moveDown() {
        currentFloor = Math.max(0, currentFloor - 1);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Integer getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(Integer targetFloor) {
        this.targetFloor = targetFloor;
    }

    public void updateTargetFloor() {
        if (isNotEmpty()) {
            targetFloor = people.first().targetFloor();
        }
    }

    public boolean isNotEmpty() {
        return !people.isEmpty();
    }

    public Direction getDirection() {
        if (isNotEmpty())
            return Direction.fromInt(currentFloor, people.first().targetFloor());
        if (targetFloor != null)
            return Direction.fromInt(currentFloor, targetFloor);
        return Direction.NONE;
    }

    public int getId() {
        return id;
    }
}
