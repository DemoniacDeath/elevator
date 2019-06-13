package com.example.elevator.domain.tasks;

public interface TaskQueue {
    void addTask(Task task);
    boolean hasNextTaskForCurrentFloor(int currentFloor);
    Task getNextTaskForCurrentFloor(int currentFloor);
}
