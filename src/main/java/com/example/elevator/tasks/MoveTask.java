package com.example.elevator.tasks;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class MoveTask implements Task {
    private final int floorNumber;
    private Runnable then = null;

    public int getFloorNumber() {
        return floorNumber;
    }

    public Runnable then() {
        return then;
    }

    @Override
    public String toString() {
        return "Go to floor #" + floorNumber;
    }
}
