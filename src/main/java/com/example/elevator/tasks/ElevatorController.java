package com.example.elevator.tasks;

public interface ElevatorController {
    void addTask(Task task);
    boolean canContinue();
    void next();
    void moveElevatorToFloor(int floorNumber);
}
