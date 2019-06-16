package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.CallTask;
import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.VIPMoveTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.MockUtil.getInvocationContainer;

@ExtendWith(MockitoExtension.class)
class ElevatorControllerVIPDecoratorTest {
    @Mock
    ElevatorController elevatorController;
    @Mock
    Elevator elevator;
    @Mock
    TaskQueue<VIPMoveTask> vipTaskQueue;
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

        when(elevatorController.getNumberOfTasks()).thenReturn(5);
        assertEquals(5, elevatorControllerVIPDecorator.getNumberOfTasks());
    }

    @Test
    void shouldAddTasks() {
        ElevatorControllerVIPDecorator elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);

        elevatorControllerVIPDecorator.addTask(callTask);
        verify(elevatorController, times(1)).addTask(callTask);
        verify(vipTaskQueue, never()).addTask(any());
        clearInvocations(elevatorController);
        getInvocationContainer(vipTaskQueue).clearInvocations();

        elevatorControllerVIPDecorator.addTask(moveTask);
        verify(elevatorController, times(1)).addTask(moveTask);
        verify(vipTaskQueue, never()).addTask(any());
        clearInvocations(elevatorController);
        getInvocationContainer(vipTaskQueue).clearInvocations();

        elevatorControllerVIPDecorator.addTask(vipMoveTask);
        verify(elevatorController, times(1)).addTask(vipMoveTask);
        verify(vipTaskQueue, times(1)).addTask(vipMoveTask);
        clearInvocations(elevatorController);
        getInvocationContainer(vipTaskQueue).clearInvocations();
    }

    @Test
    void shouldContinue() {
        ElevatorControllerVIPDecorator elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);

        when(elevator.getNumberOfFloors()).thenReturn(10);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        when(vipTaskQueue.hasNextTask()).thenReturn(false);
        when(vipTaskQueue.getNextTask()).thenReturn(null);
        when(elevatorController.canContinue()).thenReturn(false);
        assertFalse(elevatorControllerVIPDecorator.canContinue());

        when(elevatorController.canContinue()).thenReturn(true);
        assertTrue(elevatorControllerVIPDecorator.canContinue());

        when(elevatorController.canContinue()).thenReturn(false);
        when(vipTaskQueue.hasNextTask()).thenReturn(true);
        when(vipTaskQueue.getNextTask()).thenReturn(vipMoveTask);
        when(vipMoveTask.getFloorNumber()).thenReturn(7);
        assertTrue(elevatorControllerVIPDecorator.canContinue());

        when(elevatorController.canContinue()).thenReturn(true);
        assertTrue(elevatorControllerVIPDecorator.canContinue());

        when(elevatorController.getElevators()).then(i -> Stream.of(elevator));
        elevatorControllerVIPDecorator.process();
        when(vipTaskQueue.hasNextTask()).thenReturn(false);
        when(elevatorController.canContinue()).thenReturn(false);
        assertTrue(elevatorControllerVIPDecorator.canContinue());

    }

    @Test
    void shouldProcess() {
        ElevatorControllerVIPDecorator elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);

        when(elevatorController.getElevators()).then(i -> Stream.of(elevator));

        elevatorControllerVIPDecorator.process();
        verify(elevatorController, times(1)).process();
        clearInvocations(elevatorController);

        when(vipMoveTask.isComplete(elevator)).thenReturn(false);
        when(vipMoveTask.getFloorNumber()).thenReturn(7);
        when(vipTaskQueue.getNextTask()).thenReturn(vipMoveTask);
        when(elevator.getNumberOfFloors()).thenReturn(10);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        elevatorControllerVIPDecorator.addTask(vipMoveTask);
        elevatorControllerVIPDecorator.process();
        verify(elevatorController, never()).process();
        verify(elevator, times(1)).moveOneFloor(Direction.UP);
        clearInvocations(elevatorController);
        clearInvocations(elevator);

        elevatorControllerVIPDecorator = new ElevatorControllerVIPDecorator(
                elevatorController, vipTaskQueue);
        when(vipMoveTask.isComplete(elevator)).thenReturn(true);
        when(vipTaskQueue.getNextTask()).thenReturn(vipMoveTask, (VIPMoveTask) null);
        when(elevatorController.getTasksForFloorAndDirection(1, Direction.UP)).thenReturn(
                new HashSet<>(Arrays.asList(callTask, moveTask)));
        elevatorControllerVIPDecorator.addTask(vipMoveTask);
        elevatorControllerVIPDecorator.process();
        verify(elevatorController, times(1)).acceptTask(callTask);
        verify(elevatorController, times(1)).acceptTask(moveTask);
        verify(elevator, times(1)).openDoors();
        verify(elevator, times(1)).depressFloorButton();
        verify(elevator, never()).moveOneFloor(any());
        clearInvocations(elevatorController);
        clearInvocations(elevator);
    }
}