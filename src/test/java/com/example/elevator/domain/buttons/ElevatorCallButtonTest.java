package com.example.elevator.domain.buttons;


import com.example.elevator.domain.Direction;
import com.example.elevator.domain.tasks.CallTask;
import com.example.elevator.service.elevator.ElevatorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorCallButtonTest {
    @Mock
    ElevatorController elevatorController;

    @Test
    void shouldBePressed() {
        Direction direction = Direction.UP;
        int floorNumber = 99;
        ElevatorCallButton button = new ElevatorCallButton(floorNumber, direction);

        button.press(elevatorController);
        verify(elevatorController, times(1)).addTask(argThat(new CallTaskMatcher(new CallTask(
                floorNumber, direction
        ))));

        assertFalse(button.isNotPressed());

        clearInvocations(elevatorController);
        button.press(elevatorController);
        verify(elevatorController, never()).addTask(any());
    }

    public static class CallTaskMatcher implements ArgumentMatcher<CallTask> {
        private CallTask left;

        CallTaskMatcher(CallTask left) {
            this.left = left;
        }

        @Override
        public boolean matches(CallTask right) {
            return left.getFloorNumber() == right.getFloorNumber() &&
                    left.getCallDirection().equals(right.getCallDirection());
        }
    }
}