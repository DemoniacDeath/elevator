package com.example.elevator.tasks;

public interface TaskQueue {
    void addTask(Task task);

    boolean hasNextTask();

    Task getNextTask();
}
