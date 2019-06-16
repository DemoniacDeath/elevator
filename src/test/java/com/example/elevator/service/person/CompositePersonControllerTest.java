package com.example.elevator.service.person;

import com.example.elevator.domain.Floor;
import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompositePersonControllerTest {
    @Mock
    AbstractPersonController personController1;

    @Mock
    AbstractPersonController personController2;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Floor floor;

    @Mock
    Person person;

    @Test
    void shouldBeBuiltCorrectly() {
        CompositePersonController personController = new CompositePersonController(
                person, elevatorController, personController1, personController2
        );

        assertEquals(person, personController.person);
        assertEquals(elevatorController, personController.elevatorController);

    }

    @Test
    void shouldCreateDefaultPersonController() {
        CompositePersonController personController = CompositePersonController.createDefaultPersonController(elevatorController, person);
        assertTrue(personController.getControllers().stream()
                .allMatch(pc ->
                                pc instanceof EnterElevatorPersonController ||
                                pc instanceof CallElevatorPersonController ||
                                pc instanceof OverloadPreventionPersonController ||
                                pc instanceof PressFloorButtonPersonController ||
                                pc instanceof LeaveElevatorPersonController
                )
        );
    }

    @Test
    void shouldCheckForContinuation() {
        CompositePersonController personController = new CompositePersonController(
                person, elevatorController, personController1, personController2
        );

        when(person.getCurrentFloor()).thenReturn(null);
        assertTrue(personController.canContinue());

        when(person.getCurrentFloor()).thenReturn(floor);
        when(person.getDesiredFloorNumber()).thenReturn(2);
        when(person.getCurrentFloorNumber()).thenReturn(1);
        assertTrue(personController.canContinue());

        when(person.getCurrentFloorNumber()).thenReturn(2);
        assertFalse(personController.canContinue());
    }

    @Test
    void shouldComposeOthers() {
        CompositePersonController personController = new CompositePersonController(
                person, elevatorController, personController1, personController2
        );

        when(personController1.canContinue()).thenReturn(true);
        when(personController2.canContinue()).thenReturn(true);

        personController.process();
        verify(personController1, times(1)).process();
        verify(personController2, times(1)).process();
        clearInvocations(personController1);
        clearInvocations(personController2);

        when(personController1.canContinue()).thenReturn(true);
        when(personController2.canContinue()).thenReturn(false);

        personController.process();
        verify(personController1, times(1)).process();
        verify(personController2, never()).process();
        clearInvocations(personController1);
        clearInvocations(personController2);

        when(personController1.canContinue()).thenReturn(false);
        when(personController2.canContinue()).thenReturn(true);

        personController.process();
        verify(personController1, never()).process();
        verify(personController2, times(1)).process();
        clearInvocations(personController1);
        clearInvocations(personController2);

        when(personController1.canContinue()).thenReturn(false);
        when(personController2.canContinue()).thenReturn(false);

        personController.process();
        verify(personController1, never()).process();
        verify(personController2, never()).process();
        clearInvocations(personController1);
        clearInvocations(personController2);
    }
}