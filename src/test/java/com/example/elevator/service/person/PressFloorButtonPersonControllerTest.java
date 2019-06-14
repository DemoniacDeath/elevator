package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.buttons.Button;
import com.example.elevator.domain.buttons.ControlPanel;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PressFloorButtonPersonControllerTest {
    @Mock
    Person person;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Elevator elevator;

    @Mock
    ControlPanel controlPanel;

    @Mock
    Button button;

    @Mock
    Floor floor;

    @Test
    void shouldCheckForContinuation() {
        when(person.getDesiredFloorNumber()).thenReturn(12);
        PressFloorButtonPersonController personController = new PressFloorButtonPersonController(person, elevatorController);

        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getElevator()).thenReturn(null);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(null);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.getCurrentFloorNumber()).thenReturn(12);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        assertTrue(personController.canContinue());
    }

    @Test
    void shouldProcess() {
        when(person.getDesiredFloorNumber()).thenReturn(12);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.getControlPanel()).thenReturn(controlPanel);
        when(elevatorController.getElevatorControllerFor(elevator)).thenReturn(elevatorController);
        PressFloorButtonPersonController personController = new PressFloorButtonPersonController(person, elevatorController);

        when(controlPanel.getFloorButton(12)).thenReturn(null);
        personController.process();
        verify(button, never()).press(elevatorController);
        clearInvocations(button);

        when(controlPanel.getFloorButton(12)).thenReturn(button);
        when(button.isNotPressed()).thenReturn(false);
        personController.process();
        verify(button, never()).press(elevatorController);
        clearInvocations(button);

        when(controlPanel.getFloorButton(12)).thenReturn(button);
        when(button.isNotPressed()).thenReturn(true);
        personController.process();
        verify(button, times(1)).press(elevatorController);
        clearInvocations(button);


    }
}