package org.example.elevators;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {
    private ElevatorController elevatorController;
    private final static String BASE = "/api";
//    private final static String ORIGIN = "http://localhost:5173/";
//    private final static String ORIGIN = "/**";

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

//    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = BASE+"/setup", consumes = "application/json", produces = "application/json")
    public State setup(@RequestBody SetupInfo setup) {
        elevatorController = new ElevatorController(setup.floors(), setup.elevators());
        return getState();
    }

//    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = BASE+"/step", produces = "application/json")
    public State nextStep(@RequestBody State currentState) {
        elevatorController = new ElevatorController(currentState.floors(), currentState.elevators());
        elevatorController.nextStep();
        return getState();
    }

//    @CrossOrigin(origins = ORIGIN)
    @GetMapping(value = BASE+"/state", produces = "application/json")
    public State getState() {
        return new State(elevatorController.getElevatorList(), elevatorController.getFloorList());
    }
}
