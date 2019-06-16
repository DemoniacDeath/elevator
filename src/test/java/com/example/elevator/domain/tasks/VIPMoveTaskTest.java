package com.example.elevator.domain.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VIPMoveTaskTest {
    @Test
    void shouldReturnVIPString() {
        VIPMoveTask vipMoveTask = new VIPMoveTask(1);
        assertTrue(vipMoveTask.toString().contains("VIP"));
    }
}