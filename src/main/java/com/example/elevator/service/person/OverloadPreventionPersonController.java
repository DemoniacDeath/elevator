package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.AggregateElevatorController;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.info(person + " decided to leave because of elevator overload");
        person.getElevator().leave(person);
    }
}
