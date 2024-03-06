package org.example.elevators;

import org.apache.el.stream.Stream;
import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;
import org.example.elevators.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class ElevatorController {
    private int floors = 10;
    private int elevators = 3;
    private List<Elevator> elevatorList;
    private List<Floor> floorList;


    @PostMapping("/setup")
    public void setup(int floors, int elevators) {
        this.floors = floors;
        this.elevators = elevators;
        createElevators();
        createFloors();
    }

    @GetMapping("/elevators")
    public List<Elevator> getElevatorList() {
        return elevatorList;
    }

    @GetMapping("/elevators/:id")
    public Elevator getElevator(int id) {
        return elevatorList.get(id);
    }

    @GetMapping("/nextStep")
    public void nextStep() {
        generatePeople();

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
            elevatorList.stream().filter(elevator -> elevator.g)
        }
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


}
