package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class ElevatorControllerVIPDecorator implements ElevatorController {
    private final ElevatorController elevatorController;
    private final TaskQueue vipTaskQueue;
    private Task currentVIPTask = null;

    @Override
    public void addTask(Task task) {
        elevatorController.addTask(task);
    }

    @Override
    public Stream<Elevator> getElevators() {
        return elevatorController.getElevators();
    }

    @Override
    public int getNumberOfTasks() {
        return elevatorController.getNumberOfTasks();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        if (elevatorController.getElevatorControllerFor(elevator) == elevatorController) {
            return this;
        }
        return null;
    }

    @Override
    public boolean canContinue() {
        return elevatorController.canContinue();
    }

    @Override
    public void process() {
        elevatorController.process();
    }
}
