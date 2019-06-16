package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;

abstract class AbstractElevatorController {
    abstract Elevator getElevator();

    void moveElevatorTowardsFloor(int toFloorNumber) {
        if (toFloorNumber < 1) {
            throw new ElevatorControllerException("Cannot go to floor number that is less than 1");
        }
        if (toFloorNumber > getElevator().getNumberOfFloors()) {
            throw new ElevatorControllerException("Cannot go to floor number that is more than number of floors in the building");
        }
        Direction direction = Direction.compareFloors(getElevator().getCurrentFloorNumber(), toFloorNumber);
        getElevator().moveOneFloor(direction);
    }

    void processTaskCompletionOnCurrentFloor() {
        getElevator().openDoors();
        getElevator().depressFloorButton();
    }
}
