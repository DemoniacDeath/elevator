package com.example.elevator.domain.buttons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControlPanelVIPDecoratorTest {

    @Mock
    ControlPanel controlPanel;
    @Mock
    VIPControl vipControl;
    @Mock
    ElevatorStopButton stopButton;

    @Test
    void shouldDecorate() {
        ControlPanelVIPDecorator decorator = new ControlPanelVIPDecorator(controlPanel, vipControl);
        decorator.depressButtonForFloor(1);
        verify(controlPanel, times(1)).depressButtonForFloor(1);
        clearInvocations(controlPanel);

        when(controlPanel.getStopButton()).thenReturn(stopButton);
        assertEquals(stopButton, decorator.getStopButton());

        assertEquals(controlPanel, decorator.getControlPanel());
        assertEquals(vipControl, decorator.getVipControl());
    }

    @Test
    void shouldHaveOwnBehaviour() {
        ControlPanelVIPDecorator decorator = new ControlPanelVIPDecorator(controlPanel, vipControl);

        ElevatorFloorButton floorButton = decorator.getFloorButton(1);

        assertTrue(floorButton instanceof ElevatorFloorButtonVIPDecorator);

        ElevatorFloorButtonVIPDecorator decoratedButton = (ElevatorFloorButtonVIPDecorator) floorButton;

        assertEquals(vipControl, decoratedButton.getVipControl());

    }
}