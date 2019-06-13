package com.example.elevator.domain.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasNextTask() {
        return tasks.peek() != null;
    }

    public Task getNextTask() {
        return tasks.poll();
    }
}
