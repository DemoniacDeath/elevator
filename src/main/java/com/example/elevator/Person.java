package com.example.elevator;

import com.example.elevator.buttons.Button;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.example.elevator.Direction.compareFloors;

@AllArgsConstructor
@Log4j2
class Person {

    private int currentFloor;
    private int desiredFloor;

    int getCurrentFloor() {
        return currentFloor;
    }

    void getToTheDesiredFloor(Elevator elevator) {
        Button button = elevator.getBuilding().getCallPanelForFloor(currentFloor)
                .getButtonForDirection(compareFloors(currentFloor, desiredFloor));
        if (button != null) {
            button.pressAnd(() -> {
                this.enter(elevator);
                elevator.getControlPanel().getFloorButton(desiredFloor).pressAnd(() -> this.exit(elevator));
            });
        }
    }

    void enter(Elevator elevator) {
        elevator.enter(this);
    }

    void exit(Elevator elevator) {
        this.currentFloor = elevator.getCurrentFloor();
        elevator.leave(this);
    }
}
