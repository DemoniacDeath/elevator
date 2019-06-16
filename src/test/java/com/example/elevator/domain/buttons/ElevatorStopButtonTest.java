package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Elevator;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorStopButtonTest {
    @Mock
    ElevatorController elevatorController;

    @Mock
    Elevator elevator;

    @Test
    void shouldBePressed() {
        when(elevatorController.getElevators()).then(i -> Stream.of(elevator));
        ElevatorStopButton button = new ElevatorStopButton();

        assertTrue(button.isNotPressed());

        button.press(elevatorController);

        verify(elevator, times(1)).stop();
        assertFalse(button.isNotPressed());

        button.press(elevatorController);
        verify(elevator, times(1)).resume();
        assertTrue(button.isNotPressed());

    }
}