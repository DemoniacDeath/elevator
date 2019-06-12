package com.example.elevator.tasks;

import com.example.elevator.Direction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class CallTask implements Task {
    private final int floorNumber;
    private final Direction callDirection;
    private Runnable then = null;

    public Direction getCallDirection() {
        return callDirection;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Runnable then() {
        return then;
    }

    @Override
    public String toString() {
        return "Go to floor #" + floorNumber + " to then go " + callDirection;
    }
}
