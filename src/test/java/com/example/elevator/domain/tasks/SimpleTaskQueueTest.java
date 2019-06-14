package com.example.elevator.domain.tasks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SimpleTaskQueueTest {
    @Mock
    Task task1;

    @Mock
    Task task2;

    @Mock
    Task task3;

    @Test
    void shouldFunctionAsAQueue() {
        SimpleTaskQueue queue = new SimpleTaskQueue();

        assertFalse(queue.hasNextTaskForCurrentFloor(1));
        queue.addTask(task1);
        assertTrue(queue.hasNextTaskForCurrentFloor(1));
        queue.addTask(task2);
        assertTrue(queue.hasNextTaskForCurrentFloor(1));
        assertEquals(task1, queue.getNextTaskForCurrentFloor(1));
        assertTrue(queue.hasNextTaskForCurrentFloor(1));
        queue.addTask(task3);
        assertTrue(queue.hasNextTaskForCurrentFloor(1));
        assertEquals(task2, queue.getNextTaskForCurrentFloor(1));
        assertTrue(queue.hasNextTaskForCurrentFloor(1));
        assertEquals(task3, queue.getNextTaskForCurrentFloor(1));
        assertFalse(queue.hasNextTaskForCurrentFloor(1));

    }
}