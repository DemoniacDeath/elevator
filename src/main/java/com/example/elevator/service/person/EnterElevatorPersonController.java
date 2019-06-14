package com.example.elevator.service.person;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;

import java.util.stream.Stream;

public class EnterElevatorPersonController extends AbstractPersonController {
    EnterElevatorPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return person.getElevator() == null &&
                getAvailableElevators()
                    .count() > 0;
    }

    @Override
    public void process() {
        getAvailableElevators().forEach(e -> e.enter(person));
    }

    private Stream<Elevator> getAvailableElevators() {
        return elevatorController.getElevators()
                .filter(e -> e.getCurrentFloor().equals(person.getCurrentFloor()) && e.areDoorsOpen());
    }
}
