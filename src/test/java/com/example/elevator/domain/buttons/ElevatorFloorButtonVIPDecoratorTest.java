package com.example.elevator.domain.buttons;

import com.example.elevator.domain.tasks.VIPMoveTask;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorFloorButtonVIPDecoratorTest {
    @Mock
    DefaultElevatorFloorButton elevatorFloorButton;

    @Mock
    VIPControl vipControl;

    @Mock
    ElevatorController elevatorController;

    @Test
    void shouldDecorate() {
        ElevatorFloorButtonVIPDecorator elevatorFloorButtonVIPDecorator = new ElevatorFloorButtonVIPDecorator(
                elevatorFloorButton, vipControl
        );

        when(elevatorFloorButton.isNotPressed()).thenReturn(false);
        assertFalse(elevatorFloorButtonVIPDecorator.isNotPressed());
        when(elevatorFloorButton.isNotPressed()).thenReturn(true);
        assertTrue(elevatorFloorButtonVIPDecorator.isNotPressed());

        elevatorFloorButtonVIPDecorator.depress();
        verify(elevatorFloorButton, times(1)).depress();
        clearInvocations(elevatorFloorButton);

        elevatorFloorButtonVIPDecorator.setPressed();
        verify(elevatorFloorButton, times(1)).setPressed();
        clearInvocations(elevatorFloorButton);

        when(elevatorFloorButton.getFloorNumber()).thenReturn(12);
        assertEquals(12, elevatorFloorButtonVIPDecorator.getFloorNumber());
    }

    @Test
    void shouldHaveOwnBehaviour() {
        ElevatorFloorButtonVIPDecorator elevatorFloorButtonVIPDecorator = new ElevatorFloorButtonVIPDecorator(
                elevatorFloorButton, vipControl
        );

        when(vipControl.isVipMode()).thenReturn(false);
        elevatorFloorButtonVIPDecorator.press(elevatorController);
        verify(elevatorFloorButton, times(1)).press(elevatorController);
        clearInvocations(elevatorFloorButton);

        when(vipControl.isVipMode()).thenReturn(true);
        when(elevatorFloorButton.getFloorNumber()).thenReturn(12);
        when(elevatorFloorButton.isNotPressed()).thenReturn(true);
        elevatorFloorButtonVIPDecorator.press(elevatorController);
        verify(elevatorController, times(1)).addTask(argThat(new VIPMoveTaskMatcher(
                new VIPMoveTask(12)
        )));
        verify(vipControl, times(1)).deactivateVIPMode();
        clearInvocations(elevatorController);
        clearInvocations(vipControl);

        when(elevatorFloorButton.isNotPressed()).thenReturn(false);
        elevatorFloorButtonVIPDecorator.press(elevatorController);
        verify(elevatorController, never()).addTask(any());
        clearInvocations(elevatorController);
    }


    public static class VIPMoveTaskMatcher implements ArgumentMatcher<VIPMoveTask> {
        private VIPMoveTask left;

        VIPMoveTaskMatcher(VIPMoveTask left) {
            this.left = left;
        }

        @Override
        public boolean matches(VIPMoveTask right) {
            return left.getFloorNumber() == right.getFloorNumber();
        }
    }
}