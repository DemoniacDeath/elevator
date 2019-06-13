package com.example.elevator.service;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.tasks.OptimizedTaskQueue;
import com.example.elevator.domain.tasks.SimpleTaskQueue;
import com.example.elevator.domain.tasks.TaskQueue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorIntegrationTest {
    private static final int numberOfFloors = 4;
    @Test
    void testShouldMoveThreePeopleSimple() {
        shouldMoveThreePeople(new SimpleTaskQueue());
    }

    @Test
    void testShouldMoveThreePeopleOptimized() {
        shouldMoveThreePeople(new OptimizedTaskQueue(numberOfFloors));
    }

    private void shouldMoveThreePeople(TaskQueue taskQueue) {
        Building building = Building.createBuildingWith(numberOfFloors, 1);
        Elevator elevator = building.getAvailableElevator();
        ElevatorController elevatorController = new ElevatorControllerDefaultImpl(taskQueue, elevator);
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
