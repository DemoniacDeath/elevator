package com.example.elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElevatorImplTest {
    private ElevatorFactory elevatorFactory = new ElevatorFactoryImpl();

    @Test
    void testShouldMoveUpAndDown() {
        Elevator elevator = elevatorFactory.createElevator(4);
        assertEquals(1, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.UP);
        assertEquals(2, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.DOWN);
        assertEquals(1, elevator.getCurrentFloor());
    }

    @Test
    void testShouldThrowExceptionWhenMovingUpOnLastFloor() {
        Elevator elevator = elevatorFactory.createElevator(2);
        elevator.moveOneFloor(Direction.UP);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void testShouldThrowExceptionWhenMovingDownOnFirstFloor() {
        Elevator elevator = elevatorFactory.createElevator(1);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.DOWN));
    }
}