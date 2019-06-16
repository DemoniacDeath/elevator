package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.VIPerson;
import com.example.elevator.domain.buttons.*;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PressFloorButtonPersonControllerVIPDecoratorTest {

    @Mock
    PressFloorButtonPersonController pressFloorButtonPersonController;

    @Mock
    Person person;

    @Mock
    VIPerson viPerson;

    @Mock
    ElevatorController elevatorController;

    @Mock
    Elevator elevator;

    @Mock
    ControlPanel controlPanel;

    @Mock
    ControlPanelVIPDecorator controlPanelVIPDecorator;

    @Mock
    VIPControl vipControl;

    @Mock
    ElevatorFloorButtonVIPDecorator elevatorFloorButtonVIPDecorator;

    @Test
    void shouldDecorate() {
        PressFloorButtonPersonControllerVIPDecorator decorator = new PressFloorButtonPersonControllerVIPDecorator(
                pressFloorButtonPersonController);

        when(pressFloorButtonPersonController.canContinue()).thenReturn(true);
        assertTrue(decorator.canContinue());
        when(pressFloorButtonPersonController.canContinue()).thenReturn(false);
        assertFalse(decorator.canContinue());
        when(pressFloorButtonPersonController.getPerson()).thenReturn(person);
        when(person.getElevator()).thenReturn(elevator);
        when(elevator.getControlPanel()).thenReturn(controlPanel);

        decorator.process();
        verify(pressFloorButtonPersonController, times(1)).process();
        clearInvocations(pressFloorButtonPersonController);

        when(pressFloorButtonPersonController.getPerson()).thenReturn(viPerson);
        when(viPerson.getElevator()).thenReturn(elevator);
        when(elevator.getControlPanel()).thenReturn(controlPanel);
        decorator.process();
        verify(pressFloorButtonPersonController, times(1)).process();
        clearInvocations(pressFloorButtonPersonController);
    }

    @Test
    void shouldHaveOwnBehaviour() {
        PressFloorButtonPersonControllerVIPDecorator decorator = new PressFloorButtonPersonControllerVIPDecorator(
                pressFloorButtonPersonController);

        when(pressFloorButtonPersonController.getPerson()).thenReturn(viPerson);
        when(pressFloorButtonPersonController.getElevatorController()).thenReturn(elevatorController);
        when(viPerson.getElevator()).thenReturn(elevator);
        when(viPerson.getDesiredFloorNumber()).thenReturn(12);
        when(elevator.getControlPanel()).thenReturn(controlPanelVIPDecorator);
        when(controlPanelVIPDecorator.getFloorButton(12)).thenReturn(elevatorFloorButtonVIPDecorator);
        when(controlPanelVIPDecorator.getVipControl()).thenReturn(vipControl);
        when(elevatorFloorButtonVIPDecorator.isNotPressed()).thenReturn(true);
        when(elevatorController.getElevatorControllerFor(elevator)).thenReturn(elevatorController);
        decorator.process();
        verify(vipControl, times(1)).activateVIPMode();
        verify(elevatorFloorButtonVIPDecorator, times(1)).press(elevatorController);
        verify(pressFloorButtonPersonController, never()).process();
        clearInvocations(vipControl);
        clearInvocations(elevatorFloorButtonVIPDecorator);
        clearInvocations(pressFloorButtonPersonController);
    }
}