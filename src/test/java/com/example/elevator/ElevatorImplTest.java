package com.example.elevator;

import com.example.elevator.buttons.ControlPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElevatorImplTest {
    @Mock
    private Building building;

    @Mock
    private ControlPanel controlPanel;

    //Happy paths
    @Test
    void shouldMoveUpAndDown() {
        when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        assertEquals(1, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.UP);
        assertEquals(2, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.DOWN);
        assertEquals(1, elevator.getCurrentFloor());
    }

    @Test
    void shouldStop() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.stop();
        assertTrue(elevator.isStopped());
    }

    @Test
    void shouldResume() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.stop();
        elevator.stop();
        assertFalse(elevator.isStopped());
    }

    @Test
    void shouldOpenDoorsAndCloseDoors() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.openDoors();
        assertTrue(elevator.areDoorsOpen());
        elevator.closeDoors();
        assertFalse(elevator.areDoorsOpen());
    }

    @Test
    void shouldAllowAPersonToEnterAndLeave() {
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        Person person = new PersonImpl(1, 1);
        assertTrue(elevator.getPeopleInside().isEmpty());
        elevator.enter(person);
        assertEquals(1, elevator.getPeopleInside().size());
        assertTrue(elevator.getPeopleInside().contains(person));
        elevator.leave(person);
        assertTrue(elevator.getPeopleInside().isEmpty());
    }

    //Exception paths
    @Test
    void shouldThrowExceptionWhenMovingUpOnLastFloor() {
        when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.moveOneFloor(Direction.UP);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingDownOnFirstFloor() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.DOWN));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileStopped() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.stop();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileDoorsAreOpened() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new ElevatorImpl(this.building, this.controlPanel);
        elevator.openDoors();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }
}