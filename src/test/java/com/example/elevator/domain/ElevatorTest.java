package com.example.elevator.domain;

import com.example.elevator.domain.buttons.ControlPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ElevatorTest {
    @Mock
    Building building;

    @Mock
    ControlPanel controlPanel;

    @Mock
    Person person;

    @Mock
    Floor floor1;

    @Mock
    Floor floor2;

    private Elevator elevator;

    @BeforeEach
    void init() {
        lenient().when(floor1.getFloorNumber()).thenReturn(1);
        lenient().when(floor2.getFloorNumber()).thenReturn(2);
        lenient().when(building.getNumberOfFloors()).thenReturn(2);
        lenient().when(building.getFloor(1)).thenReturn(floor1);
        lenient().when(building.getFloor(2)).thenReturn(floor2);
        elevator = new Elevator("Elevator", floor1, controlPanel, 4, 1);
    }

    //Happy paths
    @Test
    void shouldMoveUpAndDown() {
        elevator.setBuilding(building);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(1, elevator.getCurrentFloor().getFloorNumber());
        elevator.moveOneFloor(Direction.UP);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(2, elevator.getCurrentFloor().getFloorNumber());
        elevator.moveOneFloor(Direction.DOWN);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(1, elevator.getCurrentFloor().getFloorNumber());
    }

    @Test
    void shouldStopAndResume() {
        assertFalse(elevator.isStopped());
        elevator.stop();
        assertTrue(elevator.isStopped());
        elevator.resume();
        assertFalse(elevator.isStopped());
    }

    @Test
    void shouldOpenDoorsAndCloseDoors() {
        elevator.openDoors();
        elevator.openDoors();
        assertTrue(elevator.areDoorsOpen());
        elevator.closeDoors();
        elevator.closeDoors();
        assertFalse(elevator.areDoorsOpen());
    }

    @Test
    void shouldAllowAPersonToEnterAndLeave() {
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
        elevator.setBuilding(building);
        elevator.moveOneFloor(Direction.UP);
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingDownOnFirstFloor() {
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.DOWN));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileStopped() {
        elevator.stop();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenMovingWhileDoorsAreOpened() {
        elevator.openDoors();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakePersonThatIsNotInTheElevatorToLeave() {
        elevator.openDoors();
        assertThrows(ElevatorException.class, () -> elevator.leave(person));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakePersonEnterOrLeaveWithClosedDoors() {
        assertThrows(ElevatorException.class, () -> elevator.enter(person));
        assertThrows(ElevatorException.class, () -> elevator.leave(person));
    }
}