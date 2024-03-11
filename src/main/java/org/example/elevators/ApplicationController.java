package org.example.elevators;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {
    private ElevatorController elevatorController;
    private final static String BASE = "/api";

    @PostMapping(value = BASE+"/setup", consumes = "application/json", produces = "application/json")
    public State setup(@RequestBody SetupInfo setup) {
        elevatorController = new ElevatorController(setup.floors(), setup.elevators());
        return getState();
    }

    @PostMapping(value = BASE+"/step", produces = "application/json")
    public State nextStep(@RequestBody State currentState) {
        elevatorController = new ElevatorController(currentState.floors(), currentState.elevators());
        elevatorController.nextStep();
        return getState();
    }

    @GetMapping(value = BASE+"/state", produces = "application/json")
    public State getState() {
        return new State(elevatorController.getElevatorList(), elevatorController.getFloorList());
    }
}
