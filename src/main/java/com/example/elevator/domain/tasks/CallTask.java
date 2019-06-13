package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
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
        return "Go to floor #" + floorNumber + " to then go " + callDirection;
    }
}
