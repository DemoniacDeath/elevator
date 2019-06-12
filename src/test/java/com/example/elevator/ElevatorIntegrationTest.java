package com.example.elevator;

import com.example.elevator.tasks.ElevatorRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElevatorIntegrationTest {
    private ElevatorFactory elevatorFactory = new ElevatorFactoryImpl();

    @Test
    void testShouldMoveThreePeople() {
        Elevator elevator = elevatorFactory.createElevator(4);
        Person person1 = new PersonImpl(1, 4);
        Person person2 = new PersonImpl(3, 2);
        Person person3 = new PersonImpl(4, 1);

        person1.getToTheDesiredFloor(elevator);
        person2.getToTheDesiredFloor(elevator);
        person3.getToTheDesiredFloor(elevator);

        ElevatorRunner.run(elevator.getElevatorController());

        assertEquals(4, person1.getCurrentFloor());
        assertEquals(2, person2.getCurrentFloor());
        assertEquals(1, person3.getCurrentFloor());
    }
}
