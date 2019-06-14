package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
abstract class AbstractElevatorController implements ElevatorController {
    @Getter
    final Elevator elevator;

    @Override
    public void moveElevatorToFloor(int floorNumber) {
        if (floorNumber < 1) {
            throw new ElevatorControllerException("Cannot go to floor number that is less than 1");
        }
        if (floorNumber > elevator.getBuilding().getNumberOfFloors()) {
            throw new ElevatorControllerException("Cannot go to floor number that is more than number of floors in the building");
        }
        while (elevator.getCurrentFloor().getFloorNumber() != floorNumber) {
            elevator.moveOneFloor(Direction.compareFloors(elevator.getCurrentFloor().getFloorNumber(), floorNumber));
        }
    }

    @Override
    public void stop() {
        elevator.stop();
    }

    @Override
    public void resume() {
        elevator.resume();
    }
}
