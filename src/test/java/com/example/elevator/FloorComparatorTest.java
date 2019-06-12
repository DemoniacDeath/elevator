package com.example.elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorComparatorTest {
    @Test
    void shouldReturnCorrectDirectionOrNull() {
        assertEquals(Direction.UP, Direction.FloorComparator.compareFloors(1, 2));
        assertEquals(Direction.DOWN, Direction.FloorComparator.compareFloors(2, 1));
        assertNull(Direction.FloorComparator.compareFloors(1, 1));
    }
}