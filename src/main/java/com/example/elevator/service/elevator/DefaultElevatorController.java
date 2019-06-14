package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.TaskRegistry;
import lombok.extern.log4j.Log4j2;

import java.util.Set;
import java.util.stream.Stream;

@Log4j2
public class DefaultElevatorController extends AbstractElevatorController {
    private final TaskQueue<MoveTask> taskQueue;
    private final TaskRegistry taskRegistry;
    private Task currentTask = null;

    public DefaultElevatorController(TaskQueue<MoveTask> taskQueue, TaskRegistry taskRegistry, Elevator elevator) {
        super(elevator);
        this.taskRegistry = taskRegistry;
        this.taskQueue = taskQueue;
    }

    @Override
    public void addTask(Task task) {
        taskRegistry.register(task);
        if (task instanceof MoveTask) {
            taskQueue.addTask((MoveTask)task);
        }
        log.info("Received a task: " + task.toString());
    }

    private void acceptTask(Task task) {
        taskRegistry.accept(task);
        if (task instanceof MoveTask) {
            taskQueue.remove((MoveTask)task);
        }
    }

    @Override
    public boolean canContinue() {
        return taskQueue.hasNextTask() || !taskRegistry.isEmpty() || currentTask != null;
    }

    @Override
    public void process() {
        elevator.closeDoors();

        if (currentTask == null) {
            currentTask = taskQueue.getNextTask();
            if (currentTask != null) {
                this.acceptTask(currentTask);
            } else {
                currentTask = taskRegistry.getAnyTaskFromFloor(elevator.getCurrentFloor().getFloorNumber());
                this.acceptTask(currentTask);
            }
        }

        Task taskToProcess = null;

        if (currentTask != null) {
            taskToProcess = currentTask;
            Set<Task> tasks = taskRegistry.getTasksForFloorAndDirection(
                    elevator.getCurrentFloor().getFloorNumber(), Direction.compareFloors(
                            elevator.getCurrentFloor().getFloorNumber(), currentTask.getFloorNumber()
                    ));
            if (!tasks.isEmpty()) {
                tasks.forEach(this::acceptTask);
                taskToProcess = tasks.iterator().next();
            }
        }

        if (taskToProcess != null) {
            if (!taskToProcess.isComplete(elevator)) {
                moveElevatorTowardsFloor(taskToProcess.getFloorNumber());
            } else {
                processTaskCompletionOnCurrentFloor();
                if (currentTask.isComplete(elevator)) {
                    currentTask = null;
                }
            }
        }
    }

    private void moveElevatorTowardsFloor(int toFloorNumber) {
        Direction direction = Direction.compareFloors(elevator.getCurrentFloor().getFloorNumber(), toFloorNumber);
        if (direction == null) {
            return;
        }
        switch (direction) {
            case DOWN:
                moveElevatorToFloor(elevator.getCurrentFloor().getFloorNumber()-1);
                break;
            case UP:
                moveElevatorToFloor(elevator.getCurrentFloor().getFloorNumber()+1);
                break;
        }
    }

    private void processTaskCompletionOnCurrentFloor() {
        elevator.openDoors();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.UP).depress();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.DOWN).depress();
        elevator.getControlPanel().getFloorButton(elevator.getCurrentFloor().getFloorNumber()).depress();
    }
}
