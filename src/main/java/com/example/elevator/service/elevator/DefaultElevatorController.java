package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.domain.tasks.TaskRegistry;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Set;
import java.util.stream.Stream;

@Log4j2
public class DefaultElevatorController extends AbstractElevatorController implements ElevatorController {
    @Getter
    private final Elevator elevator;

    private final TaskQueue<MoveTask> taskQueue;
    private final TaskRegistry taskRegistry;
    private Task currentTask = null;

    public DefaultElevatorController(TaskQueue<MoveTask> taskQueue, TaskRegistry taskRegistry, Elevator elevator) {
        this.elevator = elevator;
        this.taskRegistry = taskRegistry;
        this.taskQueue = taskQueue;
    }

    @Override
    public Stream<Elevator> getElevators() {
        return Stream.of(getElevator());
    }

    @Override
    public void addTask(Task task) {
        taskRegistry.register(task);
        if (task instanceof MoveTask) {
            taskQueue.addTask((MoveTask) task);
        }
        log.info(getElevator() + ": Received a task: " + task.toString());
    }

    @Override
    public void acceptTask(Task task) {
        taskRegistry.accept(task);
        if (task instanceof MoveTask) {
            taskQueue.remove((MoveTask) task);
        }
    }

    @Override
    public boolean canContinue() {
        return taskQueue.hasNextTask() || !taskRegistry.isEmpty() || currentTask != null;
    }

    @Override
    public void process() {
        getElevator().closeDoors();

        if (currentTask == null) {
            currentTask = taskQueue.getNextTask();
            if (currentTask != null) {
                this.acceptTask(currentTask);
            } else {
                currentTask = taskRegistry.getAnyTaskFromFloor(getElevator().getCurrentFloorNumber());
                this.acceptTask(currentTask);
            }
        }

        Task taskToProcess = getTask();

        if (taskToProcess != null) {
            if (!taskToProcess.isComplete(getElevator())) {
                moveElevatorTowardsFloor(taskToProcess.getFloorNumber());
            } else {
                processTaskCompletionOnCurrentFloor();
                if (currentTask.isComplete(getElevator())) {
                    currentTask = null;
                }
            }
        }
    }

    private Task getTask() {
        Task taskToProcess = null;

        if (currentTask != null) {
            taskToProcess = currentTask;
            Set<Task> tasks = getTasksForFloorAndDirection(
                    getElevator().getCurrentFloorNumber(), Direction.compareFloors(
                            getElevator().getCurrentFloorNumber(), currentTask.getFloorNumber()
                    )
            );
            if (!tasks.isEmpty()) {
                tasks.forEach(this::acceptTask);
                taskToProcess = tasks.iterator().next();
            }
        }
        return taskToProcess;
    }

    @Override
    public Set<Task> getTasksForFloorAndDirection(int currentFloorNumber, Direction direction) {
        return taskRegistry.getTasksForFloorAndDirection(currentFloorNumber, direction);
    }

    @Override
    public int getNumberOfTasks() {
        return this.taskRegistry.size();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        if (this.getElevator() == elevator) {
            return this;
        }
        return null;
    }
}
