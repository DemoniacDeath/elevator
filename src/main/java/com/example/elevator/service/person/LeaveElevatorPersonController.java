package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;

public class LeaveElevatorPersonController extends AbstractPersonController {
    LeaveElevatorPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return getPerson().getCurrentFloor() == null &&
                getPerson().getElevator() != null &&
                getPerson().getElevator().areDoorsOpen() &&
                getPerson().getElevator().getCurrentFloorNumber() == getPerson().getDesiredFloorNumber();
    }

    @Override
    public void process() {
        getPerson().getElevator().leave(getPerson());
    }
}
