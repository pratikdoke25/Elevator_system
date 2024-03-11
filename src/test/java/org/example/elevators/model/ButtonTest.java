package org.example.elevators.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ButtonTest {
    private Button button;

    @BeforeEach
    void setUp() {
        button = new Button();
    }

    @Test
    void testClickButton() {
        button.clickButton(Direction.UP);
        assertTrue(button.isPressed(Direction.UP));
        assertFalse(button.isPressed(Direction.DOWN));

        button.clickButton(Direction.DOWN);
        assertTrue(button.isPressed(Direction.UP));
        assertTrue(button.isPressed(Direction.DOWN));
    }

    @Test
    void testFirstClicked() {
        assertThrows(NoButtonWasPressed.class, () -> button.firstClicked());

        button.clickButton(Direction.UP);
        assertEquals(Direction.UP, button.firstClicked());

        button.clickButton(Direction.DOWN);
        assertEquals(Direction.UP, button.firstClicked());
    }

    @Test
    void testUnclickButton() {
        button.clickButton(Direction.UP);
        button.clickButton(Direction.DOWN);

        button.unclickButton(Direction.UP);
        assertFalse(button.isPressed(Direction.UP));
        assertTrue(button.isPressed(Direction.DOWN));

        button.unclickButton(Direction.DOWN);
        assertFalse(button.isPressed(Direction.UP));
        assertFalse(button.isPressed(Direction.DOWN));
    }

    @Test
    void testIsPressed() {
        assertFalse(button.isPressed(Direction.UP));
        assertFalse(button.isPressed(Direction.DOWN));

        button.clickButton(Direction.UP);
        assertTrue(button.isPressed(Direction.UP));
        assertFalse(button.isPressed(Direction.DOWN));

        button.clickButton(Direction.DOWN);
        assertTrue(button.isPressed(Direction.UP));
        assertTrue(button.isPressed(Direction.DOWN));
    }

    @Test
    void testGetPressedButtons() {
        LinkedList<Direction> pressedButtons = button.getPressedButtons();
        assertNotNull(pressedButtons);
        assertTrue(pressedButtons.isEmpty());

        button.clickButton(Direction.UP);
        pressedButtons = button.getPressedButtons();
        assertNotNull(pressedButtons);
        assertEquals(1, pressedButtons.size());
        assertTrue(pressedButtons.contains(Direction.UP));

        button.clickButton(Direction.DOWN);
        pressedButtons = button.getPressedButtons();
        assertNotNull(pressedButtons);
        assertEquals(2, pressedButtons.size());
        assertTrue(pressedButtons.contains(Direction.UP));
        assertTrue(pressedButtons.contains(Direction.DOWN));
    }
}