package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;

public class LeaveElevatorPersonController extends AbstractPersonController {
    LeaveElevatorPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() == null &&
                person.getElevator() != null && person.getElevator().equals(elevatorController.getElevator()) &&
                elevatorController.getElevator().areDoorsOpen() &&
                elevatorController.getElevator().getCurrentFloor().getFloorNumber() == person.getDesiredFloorNumber();
    }

    @Override
    public void process() {
        elevatorController.getElevator().leave(person);
    }
}
