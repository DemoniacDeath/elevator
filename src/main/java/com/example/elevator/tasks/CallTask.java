package com.example.elevator.tasks;

import com.example.elevator.Direction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallTask implements Task {
    int floorNumber;
    Direction callDirection;
    Runnable then;

    public Direction getCallDirection() {
        return callDirection;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Runnable then() {
        return then;
    }
}
