package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OverloadPreventionPersonControllerTest {
    @Mock
    Person person;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Elevator elevator;

    @Mock
    Floor floor;

    @Test
    void shouldCheckForContinuation() {
        OverloadPreventionPersonController personController = new OverloadPreventionPersonController(person, elevatorController);

        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getElevator()).thenReturn(null);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(null);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.isOverloaded()).thenReturn(false);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.isOverloaded()).thenReturn(true);
        when(elevator.areDoorsOpen()).thenReturn(false);
        assertFalse(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(null);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.isOverloaded()).thenReturn(true);
        when(elevator.areDoorsOpen()).thenReturn(true);
        assertTrue(personController.canContinue());
    }

    @Test
    void shouldProcess() {
        when(person.getElevator()).thenReturn(elevator);
        OverloadPreventionPersonController personController = new OverloadPreventionPersonController(person, elevatorController);

        personController.process();
        verify(elevator, times(1)).leave(person);
        clearInvocations(elevator);
    }

}