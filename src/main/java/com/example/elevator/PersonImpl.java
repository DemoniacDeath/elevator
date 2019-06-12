package com.example.elevator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class PersonImpl implements Person {

    private int currentFloor;
    private int desiredFloor;

    @Override
    public int getDesiredFloor() {
        return desiredFloor;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public void getToTheDesiredFloor(Elevator elevator) {
        elevator.getBuilding().getCallPanelForFloor(currentFloor)
                .getButtonForDirection(Direction.FloorComparator.compareFloors(currentFloor, desiredFloor))
                .press(() -> {
                    this.enter(elevator);
                    elevator.getControlPanel().getFloorButton(desiredFloor).press(() -> this.exit(elevator));
                });
    }

    @Override
    public void enter(Elevator elevator) {
        elevator.enter(this);
    }

    @Override
    public void exit(Elevator elevator) {
        this.currentFloor = elevator.getCurrentFloor();
        elevator.leave(this);
    }
}
