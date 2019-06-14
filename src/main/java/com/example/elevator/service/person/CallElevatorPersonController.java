package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.domain.buttons.Button;
import com.example.elevator.service.elevator.ElevatorController;

import static com.example.elevator.domain.Direction.compareFloors;

public class CallElevatorPersonController extends AbstractPersonController {

    public CallElevatorPersonController(Person person, ElevatorController elevatorController) {
        super(person, elevatorController);
    }

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() != null && person.getElevator() == null;
    }

    @Override
    public void process() {
        Button button = person.getCurrentFloor().getCallButtonForDirection(
                compareFloors(person.getCurrentFloorNumber(), person.getDesiredFloorNumber())
        );
        if (button != null && button.isNotPressed()) {
            button.press(elevatorController);
        }
    }
}
