package com.example.elevator.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DirectionTest {
    @Test
    void shouldCompareFloors() {
        assertEquals(Direction.UP, Direction.compareFloors(1, 2));
        assertEquals(Direction.DOWN, Direction.compareFloors(2, 1));
        assertNull(Direction.compareFloors(1, 1));
    }

    @Test
    void shouldCountFloorsFromCurrentInDirection() {
        assertEquals(
                Arrays.asList(1,2,3,4,5),
                Direction.countFloorsFromCurrentInDirection(1, Direction.UP, 5));
        assertEquals(
                Arrays.asList(5,4,3,2,1),
                Direction.countFloorsFromCurrentInDirection(5, Direction.DOWN, 5));
        assertEquals(
                Collections.singletonList(1),
                Direction.countFloorsFromCurrentInDirection(1, Direction.DOWN, 5));
        assertEquals(
                Collections.singletonList(5),
                Direction.countFloorsFromCurrentInDirection(5, Direction.UP, 5));
        assertEquals(
                Arrays.asList(2,3),
                Direction.countFloorsFromCurrentInDirection(2, Direction.UP, 3));

    }

    @Test
    void shouldCountFloorsBetweenTwoFloors() {
        assertEquals(
                Arrays.asList(1,2,3,4),
                Direction.countFloorsBetweenTwoFloors(1, 5)
        );
        assertEquals(
                Arrays.asList(5,4,3,2),
                Direction.countFloorsBetweenTwoFloors(5, 1)
        );
        assertEquals(
                Collections.emptyList(),
                Direction.countFloorsBetweenTwoFloors(3, 3)
        );
    }
}