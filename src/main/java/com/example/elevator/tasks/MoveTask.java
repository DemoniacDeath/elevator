package com.example.elevator.tasks;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MoveTask implements Task {
    private int floorNumber;
    private Runnable then;

    public int getFloorNumber() {
        return floorNumber;
    }

    public Runnable then() {
        return then;
    }
}
