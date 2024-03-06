package org.example.elevators.model;

public record Person(int targetFloor) {
    public static Person generateNew(int floors) {
        return new Person((int) (Math.random() * floors));
    }
}