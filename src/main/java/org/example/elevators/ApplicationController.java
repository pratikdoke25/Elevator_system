package org.example.elevators;

import org.example.elevators.model.Elevator;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {
    private ElevatorController elevatorController;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(value = "/setup", consumes = "application/json", produces = "application/json")
    public State setup(@RequestBody SetupInfo setup) {
        elevatorController = new ElevatorController(setup.floors(), setup.elevators());
        return getState();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(value = "/step", produces = "application/json")
    public State nextStep() {
        elevatorController.nextStep();
        return getState();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/state", produces = "application/json")
    public State getState() {
        return new State(elevatorController.getElevatorList(), elevatorController.getFloorList());
    }
}
