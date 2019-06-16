package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OverloadPreventionPersonController extends AbstractPersonController {
    OverloadPreventionPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return getPerson().getCurrentFloor() == null && getPerson().getElevator() != null &&
                getPerson().getElevator().isOverloaded() && getPerson().getElevator().areDoorsOpen();
    }

    @Override
    public void process() {
        log.info(getPerson() + " decided to leave because of elevator overload");
        getPerson().getElevator().leave(getPerson());
    }
}
