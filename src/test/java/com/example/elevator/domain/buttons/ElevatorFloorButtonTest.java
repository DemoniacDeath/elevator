package com.example.elevator.domain.buttons;

import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorFloorButtonTest {
    @Mock
    ElevatorController elevatorController;

    @Test
    void shouldBePressed() {
        int floorNumber = 99;
        ElevatorFloorButton button = new ElevatorFloorButton(floorNumber);

        assertEquals(floorNumber, button.getFloorNumber());

        button.press(elevatorController);
        verify(elevatorController, times(1)).addTask(argThat(new MoveTaskMatcher(new MoveTask(
                floorNumber
        ))));

        assertFalse(button.isNotPressed());

        clearInvocations(elevatorController);
        button.press(elevatorController);
        verify(elevatorController, never()).addTask(any());
    }

    public static class MoveTaskMatcher implements ArgumentMatcher<MoveTask> {
        private MoveTask left;

        MoveTaskMatcher(MoveTask left) {
            this.left = left;
        }

        @Override
        public boolean matches(MoveTask right) {
            return left.getFloorNumber() == right.getFloorNumber();
        }
    }
}