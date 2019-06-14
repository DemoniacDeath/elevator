package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TwoButtonCallPanelTest {
    @Mock
    Button upButton;

    @Mock
    Button downButton;

    @Test
    void shouldGiveCorrectButtonForGivenDirection() {
        TwoButtonCallPanel panel = new TwoButtonCallPanel(upButton, downButton);
        assertEquals(upButton, panel.getButtonForDirection(Direction.UP));
        assertEquals(downButton, panel.getButtonForDirection(Direction.DOWN));
        assertNull(panel.getButtonForDirection(null));
    }
}