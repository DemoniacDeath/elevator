package com.example.elevator;

import com.example.elevator.domain.Building;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.domain.VIPerson;
import com.example.elevator.domain.tasks.OptimizedTaskRegistry;
import com.example.elevator.domain.tasks.SimpleTaskQueue;
import com.example.elevator.domain.tasks.VIPMoveTask;
import com.example.elevator.service.ProcessRunner;
import com.example.elevator.service.Processable;
import com.example.elevator.service.SimpleCompositeProcessable;
import com.example.elevator.service.elevator.AggregateElevatorController;
import com.example.elevator.service.elevator.DefaultElevatorController;
import com.example.elevator.service.elevator.ElevatorControllerComparator;
import com.example.elevator.service.elevator.ElevatorControllerVIPDecorator;
import com.example.elevator.service.person.CompositePersonController;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
class ElevatorIntegrationTest {
    @Test
    void testShouldJustMovePeople() {
        movePeople(1, false, new HashSet<>(Arrays.asList(
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
        )));
    }

    @Test
    void testShouldMovePeopleEvenOverloaded() {
        movePeople(1, false, new HashSet<>(Arrays.asList(
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
        )));
    }

    @Test
    void testShouldMovePeopleWithTwoElevators() {
        movePeople(2, false, new HashSet<>(Arrays.asList(
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
        )));
    }

    @Test
    void testShouldMovePeopleWithVIP() {
        movePeople(1, true, new HashSet<>(Arrays.asList(
                new PersonSpec("Alice", 1, 4),
                new PersonSpec("Bob", 3, 2),
                new PersonSpec("Charlie", 4, 1),
                new PersonSpec("Dan", 10, 1),
                new PersonSpec("Erin", 1, 10, true),
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
        )));
    }

    void movePeople(final int numberOfElevators, final boolean isVIP, final Set<PersonSpec> personSpecifications) {
        Building building = Building.createBuildingWith(10, numberOfElevators, 700, isVIP);

        List<Person> people = new ArrayList<>(personSpecifications.size());
        for (PersonSpec ps : personSpecifications) {
            if (ps.isVip) {
                ps.person = VIPerson.createVIPersonOnFloorWithDesiredFloor(
                        ps.name, ps.weight, ps.desiredFloorNumber, building.getFloor(ps.currentFloor));
                people.add(ps.person);
            } else {
                ps.person = Person.createPersonOnFloorWithDesiredFloor(
                        ps.name, ps.weight, ps.desiredFloorNumber, building.getFloor(ps.currentFloor));
                people.add(ps.person);
            }
        }

        SimpleCompositeProcessable<Processable> compositeProcessable = new SimpleCompositeProcessable<>();
        AggregateElevatorController aggregateElevatorController = new AggregateElevatorController(new SimpleCompositeProcessable<>(), new ElevatorControllerComparator());
        for (Elevator elevator : building.getElevators()) {
            if (isVIP) {
                aggregateElevatorController.addProcessable(new ElevatorControllerVIPDecorator(new DefaultElevatorController(
                        new SimpleTaskQueue<>(), new OptimizedTaskRegistry(), elevator), new SimpleTaskQueue<>()));
            } else {
                aggregateElevatorController.addProcessable(new DefaultElevatorController(
                        new SimpleTaskQueue<>(), new OptimizedTaskRegistry(), elevator));
            }
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
        final boolean isVip;
        final int weight = 70;
        Person person = null;

        PersonSpec(String name, int currentFloor, int desiredFloorNumber) {
            this(name, currentFloor, desiredFloorNumber, false);
        }
    }
}
