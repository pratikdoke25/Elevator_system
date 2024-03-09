package org.example.elevators;

import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;

import java.util.List;

public record State(
        List<Elevator> elevators,
        List<Floor> floors
) {
}
