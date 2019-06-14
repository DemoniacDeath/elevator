package com.example.elevator.domain.tasks;

public interface TaskQueue<T extends Task> {
    void addTask(T task);
    boolean hasNextTask();
    T getNextTask();
    void remove(T task);
}
