package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleButtonCallPanelTest {
    @Mock
    Button button;

    @Test
    void shouldReturnSameButtonForAnyDirection() {
        SingleButtonCallPanel panel = new SingleButtonCallPanel(button);

        assertEquals(button, panel.getButtonForDirection(Direction.UP));
        assertEquals(button, panel.getButtonForDirection(Direction.DOWN));
        assertEquals(button, panel.getButtonForDirection(null));
    }
}