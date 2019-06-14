package com.example.elevator.domain.buttons;

import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ElevatorStopButtonTest {
    @Mock
    ElevatorController elevatorController;

    @Test
    void shouldBePressed() {
        ElevatorStopButton button = new ElevatorStopButton();

        assertTrue(button.isNotPressed());

        button.press(elevatorController);

        verify(elevatorController, times(1)).stop();
        assertFalse(button.isNotPressed());

        button.press(elevatorController);
        verify(elevatorController, times(1)).resume();
        assertTrue(button.isNotPressed());

    }
}