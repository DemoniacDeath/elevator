package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.VIPMoveTask;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

public class ElevatorControllerVIPDecorator extends AbstractElevatorController implements ElevatorController {
    private final ElevatorController elevatorController;
    private final TaskQueue<VIPMoveTask> vipTaskQueue;
    private Task currentVIPTask = null;

    ElevatorControllerVIPDecorator(ElevatorController elevatorController, TaskQueue<VIPMoveTask> vipTaskQueue) {
        this.elevatorController = elevatorController;
        this.vipTaskQueue = vipTaskQueue;
    }

    @Override
    Elevator getElevator() {
        return elevatorController.getElevators().findFirst().orElse(null);
    }

    @Override
    public void addTask(Task task) {
        //TODO add decoration logic here
//        elevatorController.addTask(task);
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
        //TODO add decoration logic here
//        return elevatorController.canContinue();
        return false;
    }

    @Override
    public void process() {
        //TODO add decoration logic here
//        elevatorController.process();
    }
}
