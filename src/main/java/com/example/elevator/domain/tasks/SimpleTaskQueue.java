package com.example.elevator.domain.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleTaskQueue<T extends Task> implements TaskQueue<T> {
    private final Queue<T> tasks = new LinkedList<>();

    public void addTask(T task) {
        tasks.add(task);
    }

    public boolean hasNextTask() {
        return tasks.peek() != null;
    }

    public T getNextTask() {
        return tasks.poll();
    }

    @Override
    public void remove(T task) {
        tasks.remove(task);
    }
}
