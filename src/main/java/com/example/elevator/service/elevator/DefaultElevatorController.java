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
public class DefaultElevatorController implements ElevatorController {
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
        return Stream.of(elevator);
    }

    @Override
    public void addTask(Task task) {
        taskRegistry.register(task);
        if (task instanceof MoveTask) {
            taskQueue.addTask((MoveTask) task);
        }
        log.info(elevator + ": Received a task: " + task.toString());
    }

    private void acceptTask(Task task) {
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

        Task taskToProcess = getTask();

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

    private Task getTask() {
        Task taskToProcess = null;

        if (currentTask != null) {
            taskToProcess = currentTask;
            Set<Task> tasks = getTasksForFloorAndDirection();
            if (!tasks.isEmpty()) {
                tasks.forEach(this::acceptTask);
                taskToProcess = tasks.iterator().next();
            }
        }
        return taskToProcess;
    }

    private Set<Task> getTasksForFloorAndDirection() {
        return taskRegistry.getTasksForFloorAndDirection(
                elevator.getCurrentFloor().getFloorNumber(), Direction.compareFloors(
                        elevator.getCurrentFloor().getFloorNumber(), currentTask.getFloorNumber()
                ));
    }

    private void moveElevatorTowardsFloor(int toFloorNumber) {
        if (toFloorNumber < 1) {
            throw new ElevatorControllerException("Cannot go to floor number that is less than 1");
        }
        if (toFloorNumber > elevator.getBuilding().getNumberOfFloors()) {
            throw new ElevatorControllerException("Cannot go to floor number that is more than number of floors in the building");
        }
        Direction direction = Direction.compareFloors(elevator.getCurrentFloor().getFloorNumber(), toFloorNumber);
        elevator.moveOneFloor(direction);
    }

    private void processTaskCompletionOnCurrentFloor() {
        elevator.openDoors();
        elevator.depressFloorButton();
    }

    @Override
    public void stop() {
        elevator.stop();
    }

    @Override
    public void resume() {
        elevator.resume();
    }

    @Override
    public int getNumberOfTasks() {
        return this.taskRegistry.size();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        if (this.elevator == elevator) {
            return this;
        }
        return null;
    }
}
