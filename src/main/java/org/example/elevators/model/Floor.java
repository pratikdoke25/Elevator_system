package org.example.elevators.model;

import org.example.elevators.NoButtonWasPressed;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Floor {
    private final int floorNumber;
    private List<Person> waitingUp;
    private List<Person> waitingDown;
    private final Button button;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.waitingDown = new LinkedList<>();
        this.waitingUp = new LinkedList<>();
        this.button = new Button();
    }

    public Button getButton() {
        return button;
    }

    public void clickButton(Direction direction) {
        button.clickButton(direction);
    }

    public void addPerson(Person person) {
        Direction direction = person.targetFloor() > getFloorNumber() ? Direction.UP : Direction.DOWN;
        clickButton(direction);
        switch (direction) {
            case UP -> waitingUp.add(person);
            case DOWN -> waitingDown.add(person);
        }
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
        if (elevatorDirection == Direction.UP) {
            elevator.addPerson(waitingUp);
            waitingUp = new LinkedList<>();
        } else if (elevatorDirection == Direction.DOWN) {
            elevator.addPerson(waitingDown);
            waitingDown = new LinkedList<>();
        }
//        List<Person> stillWaiting = new LinkedList<>();

//        for (Person person : waitingPeople) {
//            if (person.targetFloor() > getFloorNumber() && elevatorDirection == Direction.UP) {
//                elevator.addPerson(person);
//            } else if (person.targetFloor() < getFloorNumber() && elevatorDirection == Direction.DOWN) {
//                elevator.addPerson(person);
//            } else {
//                stillWaiting.add(person);
//            }
//        }
//        waitingPeople = stillWaiting;
    }

    public boolean hasPeopleWaiting() {
        return !getWaitingPeople().isEmpty();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Person> getWaitingPeople() {
        return Stream.concat(waitingUp.stream(), waitingDown.stream()).toList();
    }

    public List<Person> getWaitingUp() {
        return waitingUp;
    }

    public List<Person> getWaitingDown() {
        return waitingDown;
    }
}
