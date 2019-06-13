package com.example.elevator.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DirectionTest {
    @Test
    void shouldReturnCorrectDirectionOrNull() {
        assertEquals(Direction.UP, Direction.compareFloors(1, 2));
        assertEquals(Direction.DOWN, Direction.compareFloors(2, 1));
        assertNull(Direction.compareFloors(1, 1));
    }

    //TODO: add tests for other methods
}