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
        return getPerson().getElevator() == null &&
                getAvailableElevators()
                        .count() > 0;
    }

    @Override
    public void process() {
        getAvailableElevators().forEach(e -> e.enter(getPerson()));
    }

    private Stream<Elevator> getAvailableElevators() {
        return getElevatorController().getElevators()
                .filter(e -> e.getCurrentFloor().equals(getPerson().getCurrentFloor()) && e.areDoorsOpen());
    }
}
