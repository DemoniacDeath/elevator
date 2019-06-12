package com.example.elevator.tasks;

import java.util.LinkedList;
import java.util.Queue;

class TaskQueue {
    private final Queue<Task> tasks = new LinkedList<>();
    void addTask(Task task) {
        tasks.add(task);
    }

    boolean hasNextTask() {
        return tasks.peek() != null;
    }

    Task getNextTask() {
        return tasks.poll();
    }
}
