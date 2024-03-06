package org.example.elevators.model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final int id;
    private final int floors;
    private int currentFloor;
    private Integer targetFloor;
    private List<Person> people;

    public Elevator(int id, int floors) {
        this.id = id;
        this.floors = floors;
        this.currentFloor = 0;
        this.targetFloor = null;
        people = new ArrayList<>();
    }

    void letPeopleLeave() {
        people.removeIf(person -> person.targetFloor() == currentFloor);
    }
    void addPerson(Person person) {
        people.add(person);
    }
    void addPerson(List<Person> people) {
        this.people.addAll(people);
    }
    void moveUp() {
        currentFloor = Math.min(floors-1, currentFloor + 1);
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

    public Direction getDirection() {
        return targetFloor == null ? Direction.NONE : targetFloor > currentFloor ? Direction.UP : Direction.DOWN;
    }

    public int getId() {
        return id;
    }
}
