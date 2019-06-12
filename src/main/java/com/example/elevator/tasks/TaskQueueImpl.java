package com.example.elevator.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueueImpl implements TaskQueue {
    private Queue<Task> tasks = new LinkedList<>();
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
