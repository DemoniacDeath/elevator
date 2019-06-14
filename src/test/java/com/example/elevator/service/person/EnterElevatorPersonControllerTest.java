package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnterElevatorPersonControllerTest {

    @Mock
    Person person;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Elevator elevator;

    @Mock
    Floor floor1;

    @Mock
    Floor floor2;

    @Test
    void shouldCheckForContinuation() {
        EnterElevatorPersonController personController = new EnterElevatorPersonController(person, elevatorController);
        when(elevatorController.getElevators()).then(invocation -> Stream.of(elevator));
        when(person.getCurrentFloor()).thenReturn(floor1);
        when(elevator.getCurrentFloor()).thenReturn(floor2);

        when(person.getElevator()).thenReturn(elevator);
        when(elevator.areDoorsOpen()).thenReturn(false);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(false);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertFalse(personController.canContinue());

        when(elevator.getCurrentFloor()).thenReturn(floor1);

        when(person.getElevator()).thenReturn(elevator);
        when(elevator.areDoorsOpen()).thenReturn(false);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(elevator);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(false);
        assertFalse(personController.canContinue());

        when(person.getElevator()).thenReturn(null);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertTrue(personController.canContinue());
    }

    @Test
    void shouldProcess() {
        EnterElevatorPersonController personController = new EnterElevatorPersonController(person, elevatorController);
        when(elevatorController.getElevators()).then(invocation -> Stream.of(elevator));
        when(person.getCurrentFloor()).thenReturn(floor1);
        when(elevator.getCurrentFloor()).thenReturn(floor2);
        when(elevator.areDoorsOpen()).thenReturn(true);

        personController.process();
        verify(elevator, never()).enter(person);
        clearInvocations(elevator);

        when(elevator.getCurrentFloor()).thenReturn(floor1);

        personController.process();
        verify(elevator, times(1)).enter(person);
        clearInvocations(elevator);
    }
}