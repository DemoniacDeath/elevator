package com.example.elevator.domain.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleTaskQueue implements TaskQueue {
    private final Queue<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasNextTaskForCurrentFloor(int currentFloor) {
        return tasks.peek() != null;
    }

    public Task getNextTaskForCurrentFloor(int currentFloor) {
        return tasks.poll();
    }
}
