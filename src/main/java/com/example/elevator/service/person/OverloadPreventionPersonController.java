package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.AggregateElevatorController;
import com.example.elevator.service.elevator.ElevatorController;

public class OverloadPreventionPersonController extends AbstractPersonController {
    public OverloadPreventionPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() == null && person.getElevator() != null &&
                person.getElevator().isOverloaded() && person.getElevator().areDoorsOpen();
    }

    @Override
    public void process() {
        person.getElevator().leave(person);
    }
}
