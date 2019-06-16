package com.example.elevator;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.tasks.OptimizedTaskRegistry;
import com.example.elevator.domain.tasks.SimpleTaskQueue;
import com.example.elevator.service.ProcessRunner;
import com.example.elevator.service.Processable;
import com.example.elevator.service.SimpleCompositeProcessable;
import com.example.elevator.service.elevator.AggregateElevatorController;
import com.example.elevator.service.elevator.DefaultElevatorController;
import com.example.elevator.service.elevator.ElevatorControllerComparator;
import com.example.elevator.service.person.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
class ElevatorOverloadIntegrationTest {
    private static final int numberOfFloors = 10;
    private static final int numberOfElevators = 1;
    private static final Set<PersonSpec> personSpecifications = new HashSet<>(Arrays.asList(
            new PersonSpec("Alice", 1, 4),
            new PersonSpec("Bob", 1, 2),
            new PersonSpec("Charlie", 1, 3),
            new PersonSpec("Dan", 1, 7),
            new PersonSpec("Erin", 1, 10),
            new PersonSpec("Faythe", 1, 9),
            new PersonSpec("Grace", 1, 6),
            new PersonSpec("Heidi", 1, 5),
            new PersonSpec("Ivan", 1, 7),
            new PersonSpec("Judy", 1, 8),
            new PersonSpec("Mallory", 1, 9),
            new PersonSpec("Niaj", 1, 3),
            new PersonSpec("Olivia", 1, 2),
            new PersonSpec("Peggy", 1, 2),
            new PersonSpec("Rupert", 1, 2),
            new PersonSpec("Sybil", 1, 10),
            new PersonSpec("Trent", 1, 10),
            new PersonSpec("Victor", 1, 10),
            new PersonSpec("Walter", 1, 2),
            new PersonSpec("Wendy", 1, 3)
    ));

    @Test
    void testShouldMovePeopleEvenOverloaded() {
        Building building = Building.createBuildingWith(numberOfFloors, numberOfElevators, 700);

        List<Person> people = new ArrayList<>(personSpecifications.size());
        for (PersonSpec ps : personSpecifications) {
            ps.person = Person.createPersonOnFloorWithDesiredFloor(
                    ps.name, ps.weight, ps.desiredFloorNumber, building.getFloor(ps.currentFloor));
            people.add(ps.person);
        }

        SimpleCompositeProcessable<Processable> compositeProcessable = new SimpleCompositeProcessable<>();
        AggregateElevatorController aggregateElevatorController = new AggregateElevatorController(
                new SimpleCompositeProcessable<>(), new ElevatorControllerComparator());
        for (Elevator elevator : building.getElevators()) {
            aggregateElevatorController.addProcessable(new DefaultElevatorController(
                    new SimpleTaskQueue<>(), new OptimizedTaskRegistry(), elevator));
        }

        compositeProcessable.addProcessable(aggregateElevatorController);
        for (Person person : people) {
            compositeProcessable.addProcessable(
                    CompositePersonController.createDefaultPersonController(aggregateElevatorController, person)
            );
        }
        ProcessRunner.run(compositeProcessable, 1000);
        for (PersonSpec ps : personSpecifications) {
            assertNotNull(ps.person.getCurrentFloor());
            assertEquals(ps.desiredFloorNumber, ps.person.getCurrentFloorNumber());
        }
    }

    @RequiredArgsConstructor
    private static class PersonSpec {
        final String name;
        final int currentFloor;
        final int desiredFloorNumber;
        final int weight = 70;
        Person person = null;
    }
}
