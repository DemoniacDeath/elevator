package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;

import java.util.Set;

public interface TaskRegistry {
    void register(Task task);
    Set<Task> getTasksForFloorAndDirection(int floorNumber, Direction direction);
    Task getAnyTaskFromFloor(int floorNumber);
    void accept(Task task);
    boolean isEmpty();
    int size();
}
