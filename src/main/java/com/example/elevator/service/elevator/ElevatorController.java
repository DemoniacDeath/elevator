package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.Processable;

import java.util.Set;
import java.util.stream.Stream;

public interface ElevatorController extends Processable {
    void addTask(Task task);

    Stream<Elevator> getElevators();

    Set<Task> getTasksForFloorAndDirection(int currentFloorNumber, Direction direction);

    int getNumberOfTasks();

    ElevatorController getElevatorControllerFor(Elevator elevator);

    void acceptTask(Task task);
}
