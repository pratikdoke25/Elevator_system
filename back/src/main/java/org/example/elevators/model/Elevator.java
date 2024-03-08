package org.example.elevators.model;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Elevator {
    private final int id;
    private final int floors;
    private int currentFloor;
    private Integer targetFloor;
    private final Queue<Person> people;

    public Elevator(int id, int floors) {
        this.id = id;
        this.floors = floors;
        this.currentFloor = 0;
        this.targetFloor = null;
        people = new PriorityQueue<>((p1, p2) -> {
            int d1 = Math.abs(p1.targetFloor() - currentFloor);
            int d2 = Math.abs(p2.targetFloor() - currentFloor);
            return d2 - d1;
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

    void addPerson(List<Person> newPeople) {
        people.addAll(newPeople);
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

    //updates target floor to the farthest target floor of the people in the elevator
    public void updateTargetFloor() {
        if (isNotEmpty()) {
            targetFloor = people.peek().targetFloor();
        }
    }

    public boolean isNotEmpty() {
        return !people.isEmpty();
    }

    public Direction getDirection() {
        if (targetFloor != null)
            return Direction.fromInt(currentFloor, targetFloor);
        return Direction.NONE;
    }

    public List<Person> getPeople() {
        return List.copyOf(people);
    }

    public int getId() {
        return id;
    }

}
