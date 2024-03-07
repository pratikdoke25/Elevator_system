package org.example.elevators;

import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;
import org.example.elevators.model.Person;
import org.example.elevators.strategy.ElevatorStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ElevatorController {
    private final int floors;
    private final int elevators;
    private List<Elevator> elevatorList;
    private List<Floor> floorList;
    private final ElevatorStrategy strategy;

    public ElevatorController(int floors, int elevators) {
        this.floors = floors;
        this.elevators = elevators;
        strategy = new ElevatorStrategy(0, floors);
        createElevators();
        createFloors();
    }

    public void nextStep() {
        letPeopleLeaveElevator();
        generatePeople();
        letPeopleEnterElevator();
        updateElevatorsTargetFloors();
        strategy.run(elevatorList, floorList);
        moveElevators();
    }

    private void letPeopleLeaveElevator() {
        for (Elevator elevator : elevatorList) {
            elevator.letPeopleLeave();
        }
    }

    private void generatePeople() {
        for (Floor floor : floorList) {
            int onFloor = (int) (Math.random() * elevators);
            IntStream.range(0, floors)
                    .filter(f -> f != floor.getFloorNumber()).limit(onFloor)
                    .forEach(f -> floor.addPerson(Person.generateNew(floors)));
        }
    }

    private void letPeopleEnterElevator() {
        for (Floor floor : floorList) {
            for (Elevator elevator : elevatorList) {
                if (elevator.getCurrentFloor() == floor.getFloorNumber() && floor.hasPeopleWaiting()) {
                    floor.letPeopleEnterElevator(elevator);
                }
            }
        }
    }

    private void moveElevators() {
        elevatorList.forEach(Elevator::move);
    }

    //updates target floor for each not empty elevator
    private void updateElevatorsTargetFloors() {
        elevatorList.stream().filter(Elevator::isNotEmpty).forEach(Elevator::updateTargetFloor);
    }

    private void handleEmptyElevators() {
        List<Elevator> emptyElevators = elevatorList.stream().filter(e -> !e.isNotEmpty()).toList();

    }

    private void createElevators() {
        elevatorList = new ArrayList<>(elevators);
        for (int i = 0; i < elevators; i++) {
            elevatorList.add(new Elevator(i, floors));
        }
    }

    private void createFloors() {
        floorList = new ArrayList<>(floors);
        for (int i = 0; i < floors; i++) {
            floorList.add(new Floor(i));
        }
    }

    public int getElevators() {
        return elevators;
    }

    public List<Elevator> getElevatorList() {
        return elevatorList;
    }

    public Elevator getById(int id) {
        return elevatorList.get(id);
    }

    public int getFloors() {
        return floors;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }
}
