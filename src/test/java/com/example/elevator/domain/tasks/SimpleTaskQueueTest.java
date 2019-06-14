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
        SimpleTaskQueue<Task> queue = new SimpleTaskQueue<>();

        assertFalse(queue.hasNextTask());
        queue.addTask(task1);
        assertTrue(queue.hasNextTask());
        queue.addTask(task2);
        assertTrue(queue.hasNextTask());
        assertEquals(task1, queue.getNextTask());
        assertTrue(queue.hasNextTask());
        queue.addTask(task3);
        assertTrue(queue.hasNextTask());
        assertEquals(task2, queue.getNextTask());
        assertTrue(queue.hasNextTask());
        assertEquals(task3, queue.getNextTask());
        assertFalse(queue.hasNextTask());

        queue.addTask(task1);
        queue.addTask(task2);
        queue.addTask(task3);
        assertTrue(queue.hasNextTask());
        queue.remove(task2);
        assertEquals(task1, queue.getNextTask());
        assertEquals(task3, queue.getNextTask());
    }
}