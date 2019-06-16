package com.example.elevator.service.elevator;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.CallTask;
import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.TaskRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultElevatorControllerTest {

    @Mock
    TaskQueue<MoveTask> taskQueue;

    @Mock
    TaskRegistry taskRegistry;

    @Mock
    Elevator elevator;

    private MoveTask moveTask = new MoveTask(2);

    private CallTask callTask = new CallTask(1, null);

    @Mock
    Building building;

    @Test
    void shouldAddTask() {
        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        elevatorController.addTask(moveTask);
        verify(taskQueue, times(1)).addTask(moveTask);
        verify(taskRegistry, times(1)).register(moveTask);
        elevatorController.addTask(callTask);
        verify(taskRegistry, times(1)).register(callTask);
    }

    @Test
    void shouldContinue() {
        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        when(taskRegistry.isEmpty()).thenReturn(true);
        when(taskQueue.hasNextTask()).thenReturn(false);
        assertFalse(elevatorController.canContinue());
        when(taskRegistry.isEmpty()).thenReturn(false);
        when(taskQueue.hasNextTask()).thenReturn(false);
        assertTrue(elevatorController.canContinue());
        when(taskRegistry.isEmpty()).thenReturn(true);
        when(taskQueue.hasNextTask()).thenReturn(true);
        assertTrue(elevatorController.canContinue());
        when(taskRegistry.isEmpty()).thenReturn(false);
        when(taskQueue.hasNextTask()).thenReturn(true);
        assertTrue(elevatorController.canContinue());

        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        when(elevator.getNumberOfFloors()).thenReturn(3);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        elevatorController.process();
        when(taskRegistry.isEmpty()).thenReturn(true);
        when(taskQueue.hasNextTask()).thenReturn(false);
        assertTrue(elevatorController.canContinue());
        verify(elevator, times(1)).moveOneFloor(Direction.UP);
    }

    @Test
    void shouldProcess() {
        when(elevator.getNumberOfFloors()).thenReturn(3);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        when(taskRegistry.getAnyTaskFromFloor(1)).thenReturn(callTask);

        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        elevatorController.addTask(callTask);
        elevatorController.process();
        verify(elevator, times(1)).openDoors();
        clearInvocations(elevator);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        elevatorController.process();
        verify(elevator, times(1)).moveOneFloor(Direction.UP);
        clearInvocations(elevator);

        elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        when(elevator.getCurrentFloorNumber()).thenReturn(1);
        moveTask = new MoveTask(3);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        when(taskRegistry.getTasksForFloorAndDirection(1, Direction.UP))
                .thenReturn(Collections.singleton(callTask));
        elevatorController.process();
        verify(elevator, times(1)).openDoors();
        clearInvocations(elevator);


        when(elevator.getCurrentFloorNumber()).thenReturn(2);
        when(taskRegistry.getTasksForFloorAndDirection(2, Direction.UP))
                .thenReturn(Collections.emptySet());
        elevatorController.process();
        verify(elevator, times(1)).moveOneFloor(Direction.UP);
        clearInvocations(elevator);

        elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        moveTask = new MoveTask(-1);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        assertThrows(ElevatorControllerException.class, elevatorController::process);
        elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        moveTask = new MoveTask(6);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        assertThrows(ElevatorControllerException.class, elevatorController::process);
    }

    @Test
    void shouldReturnNumberOfTasks() {
        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        when(taskRegistry.size()).thenReturn(2);
        assertEquals(2, elevatorController.getNumberOfTasks());
    }

    @Test
    void shouldGetElevatorControllerForElevator() {
        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        assertEquals(elevatorController, elevatorController.getElevatorControllerFor(elevator));
        assertNull(elevatorController.getElevatorControllerFor(null));
    }

    @Test
    void shouldGetElevators() {
        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        assertEquals(1, elevatorController.getElevators().count());
        assertTrue(elevatorController.getElevators().anyMatch(e -> e == elevator));
    }
}