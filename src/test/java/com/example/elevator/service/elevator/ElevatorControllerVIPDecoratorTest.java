package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.CallTask;
import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.VIPMoveTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorControllerVIPDecoratorTest {
    @Mock
    ElevatorController elevatorController;
    @Mock
    Elevator elevator;
    @Mock
    TaskQueue vipTaskQueue;
    @Mock
    MoveTask moveTask;
    @Mock
    VIPMoveTask vipMoveTask;
    @Mock
    CallTask callTask;

    @Test
    void shouldDecorate() {
        ElevatorControllerVIPDecorator elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);

        when(elevatorController.getElevators()).thenReturn(Stream.of(elevator));
        assertTrue(elevatorControllerVIPDecorator.getElevators().allMatch(e -> e == elevator));

        when(elevatorController.getElevatorControllerFor(elevator)).thenReturn(elevatorController);
        assertEquals(elevatorControllerVIPDecorator, elevatorControllerVIPDecorator.getElevatorControllerFor(elevator));

        when(elevatorController.getElevatorControllerFor(elevator)).thenReturn(null);
        assertNull(elevatorControllerVIPDecorator.getElevatorControllerFor(elevator));
    }

    @Test
    void shouldAddTasks() {
        ElevatorControllerVIPDecorator elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);

        elevatorControllerVIPDecorator.addTask(callTask);
        verify(elevatorController, times(1)).addTask(callTask);
        clearInvocations(elevatorController);

        elevatorControllerVIPDecorator.addTask(moveTask);
        verify(elevatorController, times(1)).addTask(moveTask);
        clearInvocations(elevatorController);

        elevatorControllerVIPDecorator.addTask(vipMoveTask);
        verify(elevatorController, never()).addTask(any());
        clearInvocations(elevatorController);
    }
}