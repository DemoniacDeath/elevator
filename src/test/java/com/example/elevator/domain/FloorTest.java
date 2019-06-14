package com.example.elevator.domain;


import com.example.elevator.domain.buttons.CallPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FloorTest {
    @Mock
    private CallPanel callPanel;

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
}
