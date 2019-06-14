package com.example.elevator.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    @Test
    void shouldCreateBuilding() {
        final int numberOfFloors = 10;
        int numberOfElevators = 2;
        Building building = Building.createBuildingWith(numberOfFloors, numberOfElevators);

        assertEquals(10, building.getNumberOfFloors());
        assertNotNull(building.getAvailableElevator());
        assertEquals(numberOfElevators, building.getElevators().size());

        for (int i = 1; i <= numberOfFloors; i++) {
            assertNotNull(building.getFloor(i));
            assertEquals(i, building.getFloor(i).getFloorNumber());
            assertNotNull(building.getFloor(i).getCallPanel());
        }
    }

    @Test
    void shouldThrowException() {
        assertThrows(BuildingException.class, () -> Building.createBuildingWith(0, 1));
    }
}