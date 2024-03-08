package org.example.elevators.model;

public record Person(int targetFloor) {
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}