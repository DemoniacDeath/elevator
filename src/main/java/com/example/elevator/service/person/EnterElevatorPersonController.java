package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;

public class EnterElevatorPersonController extends AbstractPersonController {
    EnterElevatorPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return person.getElevator() == null &&
                elevatorController.getElevator().getCurrentFloor().equals(person.getCurrentFloor()) &&
                elevatorController.getElevator().areDoorsOpen();
    }

    @Override
    public void process() {
        elevatorController.getElevator().enter(person);
    }
}
