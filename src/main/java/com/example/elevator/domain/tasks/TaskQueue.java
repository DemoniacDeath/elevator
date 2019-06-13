package com.example.elevator.domain.tasks;

public interface TaskQueue {
    void addTask(Task task);
    boolean hasNextTaskFromCurrentFloor(int currentFloor);
    Task getNextTaskFromCurrentFloor(int currentFloor);
}
