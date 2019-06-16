package com.example.elevator.domain;

import com.example.elevator.domain.buttons.DefaultControlPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorTest {
    @Mock
    Building building;

    @Mock
    DefaultControlPanel controlPanel;

    @Mock
    Person person1;

    @Mock
    Person person2;

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
        lenient().when(person1.getWeight()).thenReturn(70);
        lenient().when(person2.getWeight()).thenReturn(100);
        elevator = new Elevator("Elevator", floor1, controlPanel, 4, 1, 150);
    }

    //Happy paths
    @Test
    void shouldMoveUpAndDown() {
        elevator.setBuilding(building);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(1, elevator.getCurrentFloorNumber());
        elevator.moveOneFloor(Direction.UP);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(2, elevator.getCurrentFloorNumber());
        elevator.moveOneFloor(Direction.DOWN);
        assertNotNull(elevator.getCurrentFloor());
        assertEquals(1, elevator.getCurrentFloorNumber());
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
        elevator.enter(person1);
        elevator.closeDoors();
        assertEquals(1, elevator.getPeopleInside().size());
        assertTrue(elevator.getPeopleInside().contains(person1));
        elevator.openDoors();
        elevator.leave(person1);
        elevator.closeDoors();
        assertTrue(elevator.getPeopleInside().isEmpty());
    }

    @Test
    void shouldDepressButtons() {
        elevator.depressFloorButton();
        verify(floor1).depressCallButtons();
        verify(controlPanel).depressButtonForFloor(1);
    }

    @Test
    void shouldReturnCurrentFloorNumber() {
        when(floor1.getFloorNumber()).thenReturn(12);
        assertEquals(12, elevator.getCurrentFloorNumber());
    }

    @Test
    void shouldReturnNumberOfFloors() {
        elevator.setBuilding(building);
        when(building.getNumberOfFloors()).thenReturn(12);
        assertEquals(12, elevator.getNumberOfFloors());
    }

    @Test
    void shouldOverload() {
        assertFalse(elevator.isOverloaded());
        elevator.openDoors();
        elevator.enter(person1);
        assertFalse(elevator.isOverloaded());
        elevator.enter(person2);
        assertTrue(elevator.isOverloaded());
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
        assertThrows(ElevatorException.class, () -> elevator.leave(person1));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakePersonEnterOrLeaveWithClosedDoors() {
        assertThrows(ElevatorException.class, () -> elevator.enter(person1));
        assertThrows(ElevatorException.class, () -> elevator.leave(person1));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMoveWhileOverloaded() {
        elevator.setBuilding(building);
        elevator.openDoors();
        elevator.enter(person1);
        elevator.enter(person2);
        elevator.closeDoors();
        assertThrows(ElevatorException.class, () -> elevator.moveOneFloor(Direction.UP));
    }
}