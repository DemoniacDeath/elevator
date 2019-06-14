package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Direction;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OptimizedTaskRegistry implements TaskRegistry {
    private final int numberOfFloors;
    private final List<MoveTask> moveTasks = new ArrayList<>();
    private final Set<CallTask> callTasks = new HashSet<>();

    @Override
    public void register(Task task) {
        if (task instanceof MoveTask) {
            moveTasks.add((MoveTask)task);
        }
        if (task instanceof CallTask) {
            callTasks.add((CallTask) task);
        }
    }

    @Override
    public Set<Task> getTasksForFloorAndDirection(int currentFloor, Direction direction) {
        return Stream.concat(
                callTasks.stream()
                    .filter(t -> t.getFloorNumber() == currentFloor && (
                            direction == null ||
                                    t.getCallDirection() == null ||
                                    t.getCallDirection() == direction
                    )),
                moveTasks.stream()
                    .filter(t -> t.getFloorNumber() == currentFloor)
        )
                .collect(Collectors.toSet());
    }

    @Override
    public Task getAnyTaskFromFloor(int floorNumber) {
        if (!moveTasks.isEmpty()) {
            return moveTasks.get(0);
        }
        if (!callTasks.isEmpty()) {
            return callTasks.stream().min(
                    Comparator.comparingInt(t -> t.getFloorNumber() - floorNumber)
            ).orElse(null);
        }
        return null;
    }

    @Override
    public void accept(Task task) {
        if (task instanceof MoveTask) {
            accept((MoveTask)task);
        }
        if (task instanceof CallTask) {
            accept((CallTask)task);
        }
    }

    @Override
    public boolean isEmpty() {
        return callTasks.isEmpty() && moveTasks.isEmpty();
    }

    @Override
    public int size() {
        return callTasks.size() + moveTasks.size();
    }

    private void accept(MoveTask task) {
        moveTasks.remove(task);
    }

    private void accept(CallTask task) {
        callTasks.remove(task);
    }
}
