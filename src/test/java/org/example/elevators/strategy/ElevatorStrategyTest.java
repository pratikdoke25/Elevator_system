package org.example.elevators.strategy;

import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;
import org.example.elevators.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorStrategyTest {
    private final int floors = 10;
    private ElevatorStrategy elevatorStrategy;
    private List<Elevator> elevatorList;
    private List<Floor> floorList;

    @BeforeEach
    void setUp() {
        elevatorStrategy = new ElevatorStrategy(0, floors - 1, 5);
        elevatorList = new ArrayList<>();
        floorList = new ArrayList<>();
    }

    @Test
    void testRun_WithEmptyElevatorList() {
        elevatorStrategy.run(elevatorList, floorList);
        // No assertions, just checking if the method runs without errors
    }

    @Test
    void testRun_WithElevatorsWithoutTarget() {
        Elevator elevator1 = new Elevator(0, floors);
        Elevator elevator2 = new Elevator(0, floors);
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);
        for (int i = 0; i < floors; i++)
            floorList.add(new Floor(i));

        elevatorStrategy.run(elevatorList, floorList);

        assertNull(elevator1.getTargetFloor());
        assertNull(elevator2.getTargetFloor());
    }

    @Test
    void testRun_WithCriticalFloors() {
        Elevator elevator1 = new Elevator(0, floors);
        Elevator elevator2 = new Elevator(1, floors);
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);

        for (int i = 0; i < floors; i++)
            floorList.add(new Floor(i));


        for (int i = 0; i < elevatorStrategy.getCriticalNoOfWaitingPeople() + 1; i++) {
            floorList.get(3).addPerson(new Person(2));
        }


        elevatorStrategy.run(elevatorList, floorList);

        List<Integer> targetFloors = new ArrayList<>();
        targetFloors.add(elevator1.getTargetFloor());
        targetFloors.add(elevator2.getTargetFloor());
        assertTrue(targetFloors.contains(3) && targetFloors.contains(null));
    }

}