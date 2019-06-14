package com.example.elevator.service.person;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.buttons.Button;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallElevatorPersonControllerTest {
    @Mock
    Person person;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Floor floor;

    @Mock
    Elevator elevator;

    @Mock
    Button button;

    @Test
    void shouldCheckForContinuation() {
        CallElevatorPersonController personController = new CallElevatorPersonController(person, elevatorController);
        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        assertFalse(personController.canContinue());
        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getElevator()).thenReturn(elevator);
        assertFalse(personController.canContinue());
        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(null);
        assertFalse(personController.canContinue());
        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getElevator()).thenReturn(null);
        assertTrue(personController.canContinue());
    }

    @Test
    void shouldProcess() {
        when(button.isNotPressed()).thenReturn(true);
        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getDesiredFloorNumber()).thenReturn(1);
        when(person.getCurrentFloorNumber()).thenReturn(4);
        when(floor.getCallButtonForDirection(Direction.DOWN)).thenReturn(button);
        CallElevatorPersonController personController = new CallElevatorPersonController(person, elevatorController);

        personController.process();
        verify(button, times(1)).press(elevatorController);
        verify(floor, never()).getCallButtonForDirection(Direction.UP);
        verify(floor, never()).getCallButtonForDirection(null);
        clearInvocations(button);
        clearInvocations(floor);

        when(floor.getCallButtonForDirection(Direction.UP)).thenReturn(button);
        when(person.getDesiredFloorNumber()).thenReturn(4);
        when(person.getCurrentFloorNumber()).thenReturn(1);
        personController.process();
        verify(button, times(1)).press(elevatorController);
        verify(floor, never()).getCallButtonForDirection(Direction.DOWN);
        verify(floor, never()).getCallButtonForDirection(null);
        clearInvocations(button);
        clearInvocations(floor);

        when(person.getDesiredFloorNumber()).thenReturn(1);
        personController.process();
        verify(button, never()).press(elevatorController);
        verify(floor, never()).getCallButtonForDirection(Direction.UP);
        verify(floor, never()).getCallButtonForDirection(Direction.DOWN);
        clearInvocations(button);
        clearInvocations(floor);
    }
}