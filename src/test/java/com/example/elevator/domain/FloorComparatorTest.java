package com.example.elevator.domain;

import com.example.elevator.domain.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorComparatorTest {
    @Test
    void shouldReturnCorrectDirectionOrNull() {
        assertEquals(Direction.UP, Direction.compareFloors(1, 2));
        assertEquals(Direction.DOWN, Direction.compareFloors(2, 1));
        assertNull(Direction.compareFloors(1, 1));
    }
}