package com.example.elevator.domain.tasks;

public class VIPMoveTask extends MoveTask {
    public VIPMoveTask(int floorNumber) {
        super(floorNumber);
    }

    @Override
    public String toString() {
        return super.toString() + "(VIP)";
    }
}
