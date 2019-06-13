package com.example.elevator;

import com.example.elevator.tasks.ElevatorRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElevatorIntegrationTest {
    private final BuildingFactory buildingFactory = new BuildingFactory();

    @Test
    void testShouldMoveThreePeople() {
        Building building = buildingFactory.createBuilding(4);
        Elevator elevator = building.getAvailableElevator();
        Person person1 = new Person(1, 4);
        Person person2 = new Person(3, 2);
        Person person3 = new Person(4, 1);

        person1.getToTheDesiredFloor(elevator);
        person2.getToTheDesiredFloor(elevator);
        person3.getToTheDesiredFloor(elevator);

        ElevatorRunner.run(elevator.getElevatorController());

        assertEquals(4, person1.getCurrentFloor());
        assertEquals(2, person2.getCurrentFloor());
        assertEquals(1, person3.getCurrentFloor());
    }
}
