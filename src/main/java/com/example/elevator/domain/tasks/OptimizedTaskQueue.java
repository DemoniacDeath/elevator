package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class OptimizedTaskQueue implements TaskQueue {
    private final int numberOfFloors;
    private final List<MoveTask> moveTasks = new ArrayList<>();
    private final Set<CallTask> callTasks = new HashSet<>();

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
    public boolean hasNextTaskForCurrentFloor(int currentFloor) {
        return !moveTasks.isEmpty() || !callTasks.isEmpty();
    }

    @Override
    public Task getNextTaskForCurrentFloor(int currentFloor) {
        MoveTask mainTask = getMainTask();
        if (mainTask != null) {
            Task preMainTask = getPreMainTask(mainTask, currentFloor);
            if (preMainTask != null) {
                if (preMainTask.getFloorNumber() == currentFloor) {
                    return getNextTaskForCurrentFloor(currentFloor);
                }
                return preMainTask;
            }
            return prepareTaskBeforeReturning(mainTask);
        } else {
            return prepareTaskBeforeReturning(getFirstOfRemainingCallTasks(currentFloor));
        }
    }

    private Task prepareTaskBeforeReturning(MoveTask task) {
        if (task != null) {
            moveTasks.remove(task);
        }
        return task;
    }

    private Task prepareTaskBeforeReturning(CallTask task) {
        if (task != null) {
            callTasks.remove(task);
        }
        return task;
    }

    private MoveTask getMainTask() {
        if (!moveTasks.isEmpty()) {
            return moveTasks.get(0);
        }
        return null;
    }

    private CallTask getFirstOfRemainingCallTasks(int currentFloor) {
        List<Integer> floors;
        if (currentFloor != 1) {
            floors = Direction.countFloorsFromCurrentInDirection(currentFloor, Direction.DOWN, numberOfFloors);
        } else {
            floors = Direction.countFloorsFromCurrentInDirection(currentFloor, Direction.UP, numberOfFloors);
        }
        for (Integer floor : floors) {
            Optional<CallTask> result = callTasks.stream().filter(t -> t.getFloorNumber() == floor).findAny();
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    private Task getPreMainTask(MoveTask mainTask, int currentFloor) {
        Direction direction = Direction.compareFloors(currentFloor, mainTask.getFloorNumber());
        if (direction == null) {
            return null;
        }
        for (Integer floor : Direction.countFloorsBetweenTwoFloors(currentFloor, mainTask.getFloorNumber())) {
            Optional<CallTask> callTaskResult = callTasks.stream()
                    .filter(t -> t.getFloorNumber() == floor && (
                            t.getCallDirection() == direction || t.getCallDirection() == null
                    ))
                    .findAny();
            if (callTaskResult.isPresent()) {
                return prepareTaskBeforeReturning(callTaskResult.get());
            }
            Optional<MoveTask> moveTaskResult = moveTasks.stream()
                    .filter(t -> t.getFloorNumber() == floor && t != mainTask)
                    .findAny();
            if (moveTaskResult.isPresent()) {
                return prepareTaskBeforeReturning(moveTaskResult.get());
            }
        }
        return null;
    }
}
