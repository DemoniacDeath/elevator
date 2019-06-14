package com.example.elevator.service.elevator;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Floor;
import com.example.elevator.domain.tasks.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    Floor floor;

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

        when(elevator.getCurrentFloor()).thenReturn(floor);
        when(elevator.getBuilding()).thenReturn(building);
        when(building.getNumberOfFloors()).thenReturn(3);
        when(floor.getFloorNumber()).thenReturn(1);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        elevatorController.process();
        when(taskRegistry.isEmpty()).thenReturn(true);
        when(taskQueue.hasNextTask()).thenReturn(false);
        assertTrue(elevatorController.canContinue());
        verify(elevator, times(1)).moveOneFloor(Direction.UP);
    }

    @Test
    void shouldProcess() {
        when(building.getNumberOfFloors()).thenReturn(3);
        when(elevator.getBuilding()).thenReturn(building);
        when(elevator.getCurrentFloor()).thenReturn(floor);
        when(floor.getFloorNumber()).thenReturn(1);
        when(taskRegistry.getAnyTaskFromFloor(1)).thenReturn(callTask);

        DefaultElevatorController elevatorController = new DefaultElevatorController(taskQueue, taskRegistry, elevator);
        elevatorController.addTask(callTask);
        elevatorController.process();
        verify(elevator, times(1)).openDoors();
        clearInvocations(elevator);
        when(taskQueue.getNextTask()).thenReturn(moveTask);
        elevatorController.process();
        verify(elevator, times(1)).moveOneFloor(Direction.UP);

    }
}