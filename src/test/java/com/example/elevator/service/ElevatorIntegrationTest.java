package com.example.elevator.service;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorIntegrationTest {
    @Test
    void testShouldMoveThreePeople() {
        Building building = Building.createBuildingWith(4);
        Elevator elevator = building.getAvailableElevator();
        ElevatorController elevatorController = new ElevatorControllerDefaultImpl(elevator, new DefaultTaskRunnerStrategy());
        List<Person> people = Arrays.asList(
                Person.createPersonOnFloorWithDesiredFloor("Alice", 4, building.getFloor(1)),
                Person.createPersonOnFloorWithDesiredFloor("Bob", 2, building.getFloor(3)),
                Person.createPersonOnFloorWithDesiredFloor("Charlie", 1, building.getFloor(4))
        );

        CompositeProcessor compositeProcessor = new CompositeProcessor(elevatorController);
        for (Person person : people) {
            PersonController personController = new PersonController(person, elevatorController);
            compositeProcessor.addProcessor(personController);
        }

        ProcessRunner.run(compositeProcessor);
        assertTrue(true);
        assertNotNull(people.get(0).getCurrentFloor());
        assertNotNull(people.get(1).getCurrentFloor());
        assertNotNull(people.get(2).getCurrentFloor());
        assertEquals(4, people.get(0).getCurrentFloor().getFloorNumber());
        assertEquals(2, people.get(1).getCurrentFloor().getFloorNumber());
        assertEquals(1, people.get(2).getCurrentFloor().getFloorNumber());
    }
}
