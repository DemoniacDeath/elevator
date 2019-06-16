package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveElevatorPersonControllerTest {

    @Mock
    Person person;

    @Mock
    Elevator elevator;

    @Mock
    Floor floor;

    @Test
    void shouldCheckForContinuation() {
        LeaveElevatorPersonController personController = new LeaveElevatorPersonController(person, null);
        when(person.getDesiredFloorNumber()).thenReturn(12);

        when(person.getElevator()).thenReturn(null);
        when(person.getCurrentFloor()).thenReturn(null);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(null);
        when(person.getCurrentFloor()).thenReturn(floor);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(person.getCurrentFloor()).thenReturn(floor);
        when(elevator.areDoorsOpen()).thenReturn(true);
        when(elevator.getCurrentFloorNumber()).thenReturn(12);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(person.getCurrentFloor()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(false);
        when(elevator.getCurrentFloorNumber()).thenReturn(12);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(person.getCurrentFloor()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(true);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(person.getCurrentFloor()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(true);
        when(elevator.getCurrentFloorNumber()).thenReturn(12);
        assertTrue(personController.canContinue());
    }

    @Test
    void shouldProcess() {
        LeaveElevatorPersonController personController = new LeaveElevatorPersonController(person, null);
        when(person.getElevator()).thenReturn(elevator);
        personController.process();
        verify(elevator, times(1)).leave(person);
    }
}