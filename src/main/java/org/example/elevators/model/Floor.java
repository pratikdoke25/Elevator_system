package org.example.elevators.model;

import java.util.LinkedList;
import java.util.List;

public class Floor {
    private final int floorNumber;
    private List<Person> waitingPeople;

    private Direction clickedDirection;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.waitingPeople = new LinkedList<>();
        this.clickedDirection = Direction.NONE;
    }

    public void clickButton(Direction direction) {
        clickedDirection = direction;
    }

    public void addPerson(Person person) {
        if (waitingPeople.isEmpty())
            clickButton(person.targetFloor() > getFloorNumber() ? Direction.UP : Direction.DOWN);
        waitingPeople.add(person);
    }

    public List<Person> letPeopleEnterElevator(Direction elevatorDirection) {
        clickButton(Direction.NONE);
        List<Person> people = waitingPeople;
        waitingPeople = new LinkedList<>();
        return people;
    }

    public boolean hasPeopleWaiting() {
        return !waitingPeople.isEmpty();
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
