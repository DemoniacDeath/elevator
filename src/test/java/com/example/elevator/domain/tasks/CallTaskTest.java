package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CallTaskTest {
    @Test
    void shouldConvertToString() {
        CallTask callTask = new CallTask(1, Direction.UP);
        assertTrue(callTask.toString().contains("1"));
        assertTrue(callTask.toString().contains("UP"));
        callTask = new CallTask(2, Direction.DOWN);
        assertTrue(callTask.toString().contains("2"));
        assertTrue(callTask.toString().contains("DOWN"));
        callTask = new CallTask(3, null);
        assertTrue(callTask.toString().contains("3"));
        assertTrue(callTask.toString().contains("unknown"));
    }
}