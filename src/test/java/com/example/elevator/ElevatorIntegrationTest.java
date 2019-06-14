package com.example.elevator;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.tasks.OptimizedTaskRegistry;
import com.example.elevator.domain.tasks.SimpleTaskQueue;
import com.example.elevator.service.SimpleCompositeProcessor;
import com.example.elevator.service.ProcessRunner;
import com.example.elevator.service.Processor;
import com.example.elevator.service.elevator.AggregateElevatorController;
import com.example.elevator.service.elevator.DefaultElevatorController;
import com.example.elevator.service.elevator.ElevatorControllerComparator;
import com.example.elevator.service.person.CompositePersonController;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
class ElevatorIntegrationTest {
    private static final int numberOfFloors = 10;
    private static final int numberOfElevators = 2;
    private static final Set<PersonSpec> personSpecifications = new HashSet<>(Arrays.asList(
            new PersonSpec("Alice", 1, 4),
            new PersonSpec("Bob", 3, 2),
            new PersonSpec("Charlie", 4, 1),
            new PersonSpec("Dan", 10, 1),
            new PersonSpec("Erin", 1, 10),
            new PersonSpec("Faythe", 7, 9),
            new PersonSpec("Grace", 4, 6),
            new PersonSpec("Heidi", 3, 2),
            new PersonSpec("Ivan", 1, 7),
            new PersonSpec("Judy", 2, 8),
            new PersonSpec("Mallory", 3, 9),
            new PersonSpec("Niaj", 4, 3),
            new PersonSpec("Olivia", 3, 2),
            new PersonSpec("Peggy", 2, 1),
            new PersonSpec("Rupert", 1, 2),
            new PersonSpec("Sybil", 6, 10),
            new PersonSpec("Trent", 9, 10),
            new PersonSpec("Victor", 5, 10),
            new PersonSpec("Walter", 9, 2),
            new PersonSpec("Wendy", 8, 3)
    ));

    @Test
    void testShouldMovePeople() {
        Building building = Building.createBuildingWith(numberOfFloors, numberOfElevators);

        List<Person> people = new ArrayList<>(personSpecifications.size());
        for (PersonSpec ps : personSpecifications) {
            ps.person = Person.createPersonOnFloorWithDesiredFloor(
                    ps.name, ps.desiredFloorNumber, building.getFloor(ps.currentFloor));
            people.add(ps.person);
        }

        SimpleCompositeProcessor<Processor> compositeProcessor = new SimpleCompositeProcessor<>();
        AggregateElevatorController aggregateElevatorController = new AggregateElevatorController(new SimpleCompositeProcessor<>(), new ElevatorControllerComparator());
        for (Elevator elevator : building.getElevators()) {
            aggregateElevatorController.addProcessor(new DefaultElevatorController(
                    new SimpleTaskQueue<>(), new OptimizedTaskRegistry(), elevator));
        }

        compositeProcessor.addProcessor(aggregateElevatorController);
        for (Person person : people) {
            compositeProcessor.addProcessor(
                    CompositePersonController.createDefaultCompositePersonController(person, aggregateElevatorController));
        }
        ProcessRunner.run(compositeProcessor, 1000);
        for (PersonSpec ps : personSpecifications) {
            assertNotNull(ps.person.getCurrentFloor());
            assertEquals(ps.desiredFloorNumber, ps.person.getCurrentFloor().getFloorNumber());
        }
    }

    @RequiredArgsConstructor
    private static class PersonSpec {
        final String name;
        final int currentFloor;
        final int desiredFloorNumber;
        Person person = null;
    }
}
