package com.example.elevator.service;

import com.example.elevator.domain.Person;
import com.example.elevator.domain.buttons.Button;
import lombok.RequiredArgsConstructor;

import static com.example.elevator.domain.Direction.compareFloors;

@RequiredArgsConstructor
public class PersonController implements Processor {
    private final Person person;
    private final ElevatorController elevatorController;

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() == null ||
                person.getCurrentFloor().getFloorNumber() != person.getDesiredFloorNumber();
    }

    @Override
    public void process() {
        if (person.getElevator() == null &&
                elevatorController.getElevator().getCurrentFloor().equals(person.getCurrentFloor()) &&
                elevatorController.getElevator().areDoorsOpen()
        ) {
            elevatorController.getElevator().enter(person);
        }
        if (person.getCurrentFloor() == null && person.getElevator() != null &&
                person.getElevator().equals(elevatorController.getElevator()) &&
                person.getElevator().getCurrentFloor().getFloorNumber() != person.getDesiredFloorNumber()
        ) {
            Button button = elevatorController.getElevator().getControlPanel().getFloorButton(person.getDesiredFloorNumber());
            if (button != null && button.isNotPressed()) {
                button.press(elevatorController);
            }
        }
        if (person.getCurrentFloor() != null && person.getElevator() == null) {
            Button button = person.getCurrentFloor().getCallPanel()
                    .getButtonForDirection(
                            compareFloors(person.getCurrentFloor().getFloorNumber(), person.getDesiredFloorNumber())
                    );
            if (button != null && button.isNotPressed()) {
                button.press(elevatorController);
            }
        }
        if (person.getCurrentFloor() == null &&
                person.getElevator() != null && person.getElevator().equals(elevatorController.getElevator()) &&
                elevatorController.getElevator().areDoorsOpen() &&
                elevatorController.getElevator().getCurrentFloor().getFloorNumber() == person.getDesiredFloorNumber()
        ) {
            elevatorController.getElevator().leave(person);
        }
    }
}
