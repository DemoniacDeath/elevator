package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.VIPMoveTask;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Stream;

public class ElevatorControllerVIPDecorator extends AbstractElevatorController implements ElevatorController {
    private final ElevatorController elevatorController;
    private final TaskQueue<VIPMoveTask> vipTaskQueue;
    @Getter
    private Task currentVIPTask = null;

    public ElevatorControllerVIPDecorator(ElevatorController elevatorController, TaskQueue<VIPMoveTask> vipTaskQueue) {
        this.elevatorController = elevatorController;
        this.vipTaskQueue = vipTaskQueue;
    }

    @Override
    Elevator getElevator() {
        return elevatorController.getElevators().findFirst().orElse(null);
    }

    @Override
    public void addTask(Task task) {
        if (task instanceof VIPMoveTask) {
            this.vipTaskQueue.addTask((VIPMoveTask) task);
        }
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
        return elevatorController.canContinue() || vipTaskQueue.hasNextTask() || currentVIPTask != null;
    }

    @Override
    public void acceptTask(Task task) {
        this.elevatorController.acceptTask(task);
        if (task instanceof VIPMoveTask) {
            this.vipTaskQueue.remove((VIPMoveTask) task);
        }
    }

    @Override
    public void process() {
        getElevator().closeDoors();

        if (currentVIPTask == null) {
            currentVIPTask = vipTaskQueue.getNextTask();
            if (currentVIPTask != null) {
                this.acceptTask(currentVIPTask);
            }
        }

        if (currentVIPTask != null) {
            if (currentVIPTask.isComplete(getElevator())) {
                getTasksForFloorAndDirection(getElevator().getCurrentFloorNumber(),
                        Direction.compareFloors(
                                getElevator().getCurrentFloorNumber(), currentVIPTask.getFloorNumber()
                        )).forEach(this::acceptTask);
                currentVIPTask = null;
                processTaskCompletionOnCurrentFloor();
            } else {
                moveElevatorTowardsFloor(currentVIPTask.getFloorNumber());
            }
        } else {
            elevatorController.process();
        }
    }

    @Override
    public Set<Task> getTasksForFloorAndDirection(int currentFloorNumber, Direction direction) {
        return elevatorController.getTasksForFloorAndDirection(currentFloorNumber, direction);
    }
}
