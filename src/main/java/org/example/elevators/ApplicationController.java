package org.example.elevators;

import org.example.elevators.model.Elevator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class ApplicationController {
    private ElevatorController elevatorController = new ElevatorController(10, 3);

    @PostMapping("/setup")
    public void setup(int floors, int elevators) {
        elevatorController = new ElevatorController(floors, elevators);
    }

    @GetMapping("/elevators")
    public List<Elevator> getElevatorList() {
        return elevatorController.getElevatorList();
    }

    @GetMapping("/elevators/:id")
    public Elevator getElevator(int id) {
        return elevatorController.getById(id);
    }

    @GetMapping("/nextStep")
    public void nextStep() {
        elevatorController.nextStep();


    }

}
