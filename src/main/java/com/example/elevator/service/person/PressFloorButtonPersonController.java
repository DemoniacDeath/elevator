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
        return person.getCurrentFloor() == null && person.getElevator() != null &&
                person.getElevator().getCurrentFloor().getFloorNumber() != person.getDesiredFloorNumber();
    }

    @Override
    public void process() {
        Button button = person.getElevator().getControlPanel().getFloorButton(person.getDesiredFloorNumber());
        if (button != null && button.isNotPressed()) {
            button.press(elevatorController.getElevatorControllerFor(person.getElevator()));
        }
    }
}
