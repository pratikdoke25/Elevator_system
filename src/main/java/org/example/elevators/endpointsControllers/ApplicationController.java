package org.example.elevators.endpointsControllers;

import org.example.elevators.ElevatorController;
import org.example.elevators.SetupInfo;
import org.example.elevators.State;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {
    private final static String BASE = "/api";
    private ElevatorController elevatorController;

    @PostMapping(value = BASE + "/setup", consumes = "application/json", produces = "application/json")
    public State setup(@RequestBody SetupInfo setup) {
        elevatorController = new ElevatorController(setup.floors(), setup.elevators());
        return getState();
    }

    @PostMapping(value = BASE + "/step", produces = "application/json")
    public State nextStep(@RequestBody State currentState) {
        elevatorController = new ElevatorController(currentState.floors(), currentState.elevators());
        elevatorController.nextStep();
        return getState();
    }

    @GetMapping(value = BASE + "/state", produces = "application/json")
    public State getState() {
        return new State(elevatorController.getElevatorList(), elevatorController.getFloorList());
    }
}
