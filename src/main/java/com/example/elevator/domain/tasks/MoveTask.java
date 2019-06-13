package com.example.elevator.domain.tasks;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoveTask implements Task {
    private final int floorNumber;

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        return "Go to floor #" + floorNumber;
    }
}
