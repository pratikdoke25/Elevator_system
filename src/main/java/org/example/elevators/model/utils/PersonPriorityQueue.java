package org.example.elevators.model.utils;

import org.example.elevators.model.Person;

import java.util.List;
import java.util.PriorityQueue;

public class PersonPriorityQueue extends PriorityQueue<Person> {
    public PersonPriorityQueue(int currentFloor) {
        super((p1, p2) -> {
            int d1 = Math.abs(p1.targetFloor() - currentFloor);
            int d2 = Math.abs(p2.targetFloor() - currentFloor);
            return d2 - d1;
        });
    }

    public PersonPriorityQueue(int currentFloor, List<Person> people) {
        super(currentFloor);
        addAll(people);
    }
}
