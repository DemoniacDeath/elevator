package com.example.elevator.domain;


import com.example.elevator.domain.buttons.Button;
import com.example.elevator.domain.buttons.CallPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FloorTest {
    @Mock
    CallPanel callPanel;

    @Mock
    Button button;

    @Test
    void shouldConvertToString() {
        Floor floor = new Floor(callPanel, 123);
        assertTrue(floor.toString().contains("123"));
    }

    @Test
    void shouldDepressCallButtons() {
        Floor floor = new Floor(callPanel, 123);
        floor.depressCallButtons();
        verify(callPanel, times(1)).depressButtons();
    }

    @Test
    void shouldReturnCallButtonForDirection() {
        when(callPanel.getButtonForDirection(any())).thenReturn(button);
        Floor floor = new Floor(callPanel, 123);
        assertEquals(button, floor.getCallButtonForDirection(null));
        assertEquals(button, floor.getCallButtonForDirection(Direction.UP));
        assertEquals(button, floor.getCallButtonForDirection(Direction.DOWN));
    }
}
