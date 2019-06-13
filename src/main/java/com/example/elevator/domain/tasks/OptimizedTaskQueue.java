package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.example.elevator.domain.Direction.*;

@RequiredArgsConstructor
public class OptimizedTaskQueue implements TaskQueue {
    private final int numberOfFloors;
    private final List<MoveTask> moveTasks = new ArrayList<>();
    private final Set<CallTask> callTasks = new HashSet<>();
    private Direction lastDirection = null;

    @Override
    public void addTask(Task task) {
        if (task instanceof MoveTask) {
            moveTasks.add((MoveTask)task);
        }
        if (task instanceof CallTask) {
            callTasks.add((CallTask) task);
        }
    }

    @Override
    public boolean hasNextTaskFromCurrentFloor(int currentFloor) {
        return !moveTasks.isEmpty() || !callTasks.isEmpty();
    }

    @Override
    public Task getNextTaskFromCurrentFloor(int currentFloor) {
        if (!callTasks.isEmpty()) {
            Optional<CallTask> result = callTasks.stream().filter(t -> t.getFloorNumber() == currentFloor).findAny();
            if (result.isPresent()) {
                return prepareResultForCallTask(result.get(), currentFloor);
            }
            Direction lookupDirection;
            if ((lastDirection == null || lastDirection == UP) && currentFloor != numberOfFloors || lastDirection == DOWN && currentFloor == 1) {
                lookupDirection = UP;
            } else {
                lookupDirection = DOWN;
            }
            result = callTasks.stream().filter(t -> t.getCallDirection() == lookupDirection)
                    .sorted(Comparator.comparingInt(a -> (a.getFloorNumber() - currentFloor)))
                    .findAny();
            if (result.isPresent()) {
                return prepareResultForCallTask(result.get(), currentFloor);
            }
        }
        if (!moveTasks.isEmpty()) {
            MoveTask task = moveTasks.remove(0);
            lastDirection = compareFloors(currentFloor, task.getFloorNumber());
            return task;
        }
        return null;
    }

    private Task prepareResultForCallTask(CallTask callTask, int currentFloor) {
        Direction newDirection = compareFloors(currentFloor, callTask.getFloorNumber());
        if (lastDirection != null && newDirection == null) {
            callTasks.remove(callTask);
            return getNextTaskFromCurrentFloor(currentFloor);
        }
        lastDirection = newDirection;
        callTasks.remove(callTask);
        return callTask;
    }
}
