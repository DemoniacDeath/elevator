package com.example.elevator.domain;

import com.example.elevator.domain.buttons.ControlPanelVIPDecorator;
import com.example.elevator.domain.tasks.VIPMoveTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    @Test
    void shouldCreateBuilding() {
        final int numberOfFloors = 10;
        int numberOfElevators = 2;
        Building building = Building.createBuildingWith(numberOfFloors, numberOfElevators, 150);

        assertEquals(10, building.getNumberOfFloors());
        assertNotNull(building.getAvailableElevator());
        assertEquals(numberOfElevators, building.getElevators().size());

        for (int i = 1; i <= numberOfFloors; i++) {
            assertNotNull(building.getFloor(i));
            assertEquals(i, building.getFloor(i).getFloorNumber());
            assertNotNull(building.getFloor(i).getCallPanel());
        }

        for (Elevator elevator : building.getElevators()) {
            assertFalse(elevator.getControlPanel() instanceof ControlPanelVIPDecorator);
        }
        building = Building.createBuildingWith(numberOfFloors, numberOfElevators, 150, true);
        for (Elevator elevator : building.getElevators()) {
            assertTrue(elevator.getControlPanel() instanceof ControlPanelVIPDecorator);
        }
    }

    @Test
    void shouldThrowException() {
        assertThrows(BuildingException.class, () -> Building.createBuildingWith(0, 1, 150));
    }
}