package com.example.elevator.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    @Test
    void shouldCreateBuilding() {
        final int numberOfFloors = 10;
        Building building = Building.createBuildingWith(10, 10);

        assertEquals(10, building.getNumberOfFloors());
        assertNotNull(building.getAvailableElevator());

        for (int i = 1; i <= numberOfFloors; i++) {
            assertNotNull(building.getFloor(i));
            assertEquals(i, building.getFloor(i).getFloorNumber());
            assertNotNull(building.getFloor(i).getCallPanel());
        }
    }
}