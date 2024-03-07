package org.example.elevators.model;

import org.example.elevators.NoButtonWasPressed;

import java.util.LinkedList;


public class Button {
    private final LinkedList<Direction> pressedButtons = new LinkedList<>();

    public void clickButton(Direction direction) {
        if (!pressedButtons.contains(direction)) {
            pressedButtons.add(direction);
        }
    }

    public Direction firstClicked() throws NoButtonWasPressed {
        try {
            return pressedButtons.getFirst();
        } catch (Exception e) {
            throw new NoButtonWasPressed();
        }
    }

    public void unclickButton(Direction direction) {
        pressedButtons.remove(direction);
    }

    public boolean isPressed(Direction direction) {
        return pressedButtons.contains(direction);
    }
    public LinkedList<Direction> getPressedButtons() {
        return pressedButtons;
    }

}
