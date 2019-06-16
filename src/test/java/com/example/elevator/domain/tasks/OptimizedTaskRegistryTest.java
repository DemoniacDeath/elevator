package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptimizedTaskRegistryTest {
    @Mock
    CallTask callTask1;

    @Mock
    CallTask callTask2;

    @Mock
    MoveTask moveTask1;

    @Mock
    MoveTask moveTask2;

    @Test
    void shouldRegisterATaskAndLetATaskBeAccepted() {
        OptimizedTaskRegistry taskRegistry = new OptimizedTaskRegistry();
        assertTrue(taskRegistry.isEmpty());
        assertEquals(0, taskRegistry.size());
        taskRegistry.register(callTask1);
        assertFalse(taskRegistry.isEmpty());
        assertEquals(1, taskRegistry.size());
        taskRegistry.register(moveTask1);
        assertFalse(taskRegistry.isEmpty());
        assertEquals(2, taskRegistry.size());
        taskRegistry.accept(callTask1);
        assertFalse(taskRegistry.isEmpty());
        assertEquals(1, taskRegistry.size());
        taskRegistry.accept(callTask1);
        assertFalse(taskRegistry.isEmpty());
        assertEquals(1, taskRegistry.size());
        taskRegistry.accept(moveTask1);
        assertTrue(taskRegistry.isEmpty());
        assertEquals(0, taskRegistry.size());
        taskRegistry.accept(moveTask1);
        assertTrue(taskRegistry.isEmpty());
        assertEquals(0, taskRegistry.size());
    }

    @Test
    void shouldGetAnyTaskFromFloor() {
        OptimizedTaskRegistry taskRegistry = new OptimizedTaskRegistry();
        assertNull(taskRegistry.getAnyTaskFromFloor(1));

        taskRegistry.register(moveTask1);
        taskRegistry.register(callTask1);


        assertEquals(moveTask1, taskRegistry.getAnyTaskFromFloor(1));

        taskRegistry.accept(moveTask1);
        assertEquals(callTask1, taskRegistry.getAnyTaskFromFloor(1));
        taskRegistry.accept(callTask1);

        taskRegistry.register(moveTask2);
        taskRegistry.register(moveTask1);
        assertEquals(moveTask2, taskRegistry.getAnyTaskFromFloor(1));

        taskRegistry.accept(moveTask1);
        taskRegistry.accept(moveTask2);

        taskRegistry.register(callTask1);
        taskRegistry.register(callTask2);

        when(callTask1.getFloorNumber()).thenReturn(4);
        when(callTask2.getFloorNumber()).thenReturn(1);

        assertEquals(callTask1, taskRegistry.getAnyTaskFromFloor(5));
        assertEquals(callTask1, taskRegistry.getAnyTaskFromFloor(4));
        assertEquals(callTask1, taskRegistry.getAnyTaskFromFloor(3));
        assertEquals(callTask2, taskRegistry.getAnyTaskFromFloor(2));
        assertEquals(callTask2, taskRegistry.getAnyTaskFromFloor(1));
    }

    @Test
    void shouldGetTasksForFloorAndDirection() {
        when(callTask1.getFloorNumber()).thenReturn(4);
        when(callTask2.getFloorNumber()).thenReturn(1);
        when(callTask1.getCallDirection()).thenReturn(Direction.DOWN);
        when(callTask2.getCallDirection()).thenReturn(null);
        when(moveTask1.getFloorNumber()).thenReturn(5);
        when(moveTask2.getFloorNumber()).thenReturn(1);

        OptimizedTaskRegistry taskRegistry = new OptimizedTaskRegistry();
        assertEquals(emptySet(), taskRegistry.getTasksForFloorAndDirection(1, Direction.UP));
        taskRegistry.register(moveTask1);
        taskRegistry.register(callTask1);
        assertEquals(singleton(moveTask1), taskRegistry.getTasksForFloorAndDirection(5, Direction.DOWN));
        assertEquals(emptySet(), taskRegistry.getTasksForFloorAndDirection(4, Direction.UP));
        assertEquals(singleton(callTask1), taskRegistry.getTasksForFloorAndDirection(4, Direction.DOWN));
        assertEquals(singleton(callTask1), taskRegistry.getTasksForFloorAndDirection(4, null));
        assertEquals(emptySet(), taskRegistry.getTasksForFloorAndDirection(1, Direction.UP));

        taskRegistry.register(moveTask2);
        taskRegistry.register(callTask2);
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, Direction.UP).contains(callTask2));
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, Direction.UP).contains(moveTask2));
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, null).contains(moveTask2));
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, null).contains(callTask2));
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, Direction.DOWN).contains(moveTask2));
        assertTrue(taskRegistry.getTasksForFloorAndDirection(1, Direction.DOWN).contains(callTask2));
    }
}