package org.example.elevators.model;

import org.example.elevators.NoButtonWasPressed;

import java.util.LinkedList;
import java.util.List;

public class Floor {
    private final int floorNumber;
    private List<Person> waitingPeople;

    private final Button button;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.waitingPeople = new LinkedList<>();
        this.button = new Button();
    }

    public Button getButton() {
        return button;
    }

    public void clickButton(Direction direction) {
        button.clickButton(direction);
    }

    public void addPerson(Person person) {
        if (waitingPeople.isEmpty())
            clickButton(person.targetFloor() > getFloorNumber() ? Direction.UP : Direction.DOWN);
        waitingPeople.add(person);
    }

    public void letPeopleEnterElevator(Elevator elevator) {
        if (elevator.getDirection() == Direction.NONE)
            try {
                Direction firstClicked = button.firstClicked();
                elevator.setTargetFloor(firstClicked == Direction.UP ? floorNumber + 1 : floorNumber - 1);
            } catch (
                    NoButtonWasPressed e) {
                e.printStackTrace();
            }
        Direction elevatorDirection = elevator.getDirection();
        button.unclickButton(elevatorDirection);
        List<Person> stillWaiting = new LinkedList<>();
        for (Person person : waitingPeople) {
            if (person.targetFloor() > getFloorNumber() && elevatorDirection == Direction.UP) {
                elevator.addPerson(person);
            } else if (person.targetFloor() < getFloorNumber() && elevatorDirection == Direction.DOWN) {
                elevator.addPerson(person);
            } else {
                stillWaiting.add(person);
            }
        }
        waitingPeople = stillWaiting;
    }

    public boolean hasPeopleWaiting() {
        return !waitingPeople.isEmpty();
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
