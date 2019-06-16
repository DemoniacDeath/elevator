package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.domain.buttons.Button;
import com.example.elevator.service.elevator.ElevatorController;

public class PressFloorButtonPersonController extends AbstractPersonController {
    PressFloorButtonPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return getPerson().getCurrentFloor() == null && getPerson().getElevator() != null &&
                getPerson().getElevator().getCurrentFloorNumber() != getPerson().getDesiredFloorNumber();
    }

    @Override
    public void process() {
        Button button = getPerson().getElevator().getControlPanel().getFloorButton(getPerson().getDesiredFloorNumber());
        if (button != null && button.isNotPressed()) {
            button.press(getElevatorController().getElevatorControllerFor(getPerson().getElevator()));
        }
    }
}
