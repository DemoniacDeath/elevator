package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CallTask implements Task {
    private final int floorNumber;
    private final Direction callDirection;

    public Direction getCallDirection() {
        return callDirection;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        if (callDirection != null) {
            return "Go to floor #" + floorNumber + " to then go " + callDirection;
        }
        return "Go to floor #" + floorNumber + " to then go in unknown direction";
    }

    @Override
    public boolean isComplete(Elevator elevator) {
        return elevator.getCurrentFloorNumber() == floorNumber;
    }
}
