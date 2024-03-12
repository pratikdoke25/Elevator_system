package org.example.elevators;

import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;
import org.example.elevators.model.Person;
import org.example.elevators.strategy.ElevatorStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElevatorController {
    private final int floorsNo;
    private final int elevatorsNo;
    private final ElevatorStrategy strategy;
    private List<Elevator> elevatorList;
    private List<Floor> floorList;

    public ElevatorController(int floors, int elevators) {
        this.floorsNo = floors;
        this.elevatorsNo = elevators;
        strategy = new ElevatorStrategy(0, floors - 1, 5);
        createElevators();
        createFloors();
    }

    public ElevatorController(List<Floor> floors, List<Elevator> elevators) {
        this.floorsNo = floors.size();
        this.elevatorsNo = elevators.size();
        strategy = new ElevatorStrategy(0, floorsNo - 1, 5);
        this.elevatorList = elevators;
        this.floorList = floors;
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
        elevatorList.forEach(Elevator::letPeopleLeave);
    }

    private void generatePeople() {
        floorList.stream().filter(t -> Math.random() > 0.5)
                .forEach(floor -> {
                    int onFloor = (int) (Math.random() * elevatorsNo) + 1;
                    new Random().ints(0, floorsNo)
                            .filter(f -> f != floor.getFloorNumber()).limit(onFloor)
                            .forEach(f -> floor.addPerson(new Person(f)));
                });
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
        elevatorList.forEach(Elevator::updateTargetFloor);
    }

    private void createElevators() {
        elevatorList = new ArrayList<>(elevatorsNo);
        for (int i = 0; i < elevatorsNo; i++) {
            elevatorList.add(new Elevator(i, floorsNo));
        }
    }

    private void createFloors() {
        floorList = new ArrayList<>(floorsNo);
        for (int i = 0; i < floorsNo; i++) {
            floorList.add(new Floor(i));
        }
    }

    public int getElevatorsNo() {
        return elevatorsNo;
    }

    public List<Elevator> getElevatorList() {
        return elevatorList;
    }

    public Elevator getById(int id) {
        return elevatorList.get(id);
    }

    public int getFloorsNo() {
        return floorsNo;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }
}
