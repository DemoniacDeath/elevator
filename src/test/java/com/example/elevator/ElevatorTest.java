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
class ElevatorTest {
    @Mock
    Building building;

    @Mock
    ControlPanel controlPanel;

    @Mock
    Person person;

    //Happy paths
    @Test
    void shouldMoveUpAndDown() {
        when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        assertEquals(1, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.UP);
        assertEquals(2, elevator.getCurrentFloor());
        elevator.moveOneFloor(Direction.DOWN);
        assertEquals(1, elevator.getCurrentFloor());
    }

    @Test
    void shouldStop() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.stop();
        assertTrue(elevator.isStopped());
    }

    @Test
    void shouldResume() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.stop();
        elevator.stop();
        assertFalse(elevator.isStopped());
    }

    @Test
    void shouldOpenDoorsAndCloseDoors() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.openDoors();
        assertTrue(elevator.areDoorsOpen());
        elevator.closeDoors();
        assertFalse(elevator.areDoorsOpen());
    }

    @Test
    void shouldAllowAPersonToEnterAndLeave() {
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        assertTrue(elevator.getPeopleInside().isEmpty());
        elevator.openDoors();
        elevator.enter(person);
        elevator.closeDoors();
        assertEquals(1, elevator.getPeopleInside().size());
        assertTrue(elevator.getPeopleInside().contains(person));
        elevator.openDoors();
        elevator.leave(person);
        elevator.closeDoors();
        assertTrue(elevator.getPeopleInside().isEmpty());
    }

    //Exception paths
    @Test
    void shouldThrowExceptionWhenMovingUpOnLastFloor() {
        when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.moveOneFloor(Direction.UP);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingDownOnFirstFloor() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.DOWN));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileStopped() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.stop();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileDoorsAreOpened() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.openDoors();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakePersonThatIsNotInTheElevatorToLeave() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        elevator.openDoors();
        assertThrows(ElevatorException.class, () -> elevator.leave(person));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakePersonEnterOrLeaveWithClosedDoors() {
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        Elevator elevator = new Elevator(this.building, this.controlPanel);
        assertThrows(ElevatorException.class, () -> elevator.enter(person));
        assertThrows(ElevatorException.class, () -> elevator.leave(person));
    }
}