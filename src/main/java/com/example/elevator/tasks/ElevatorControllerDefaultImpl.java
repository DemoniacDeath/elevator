package com.example.elevator.tasks;

import com.example.elevator.Direction;
import com.example.elevator.Elevator;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElevatorControllerDefaultImpl implements ElevatorController {
    private final TaskQueue taskQueue = new TaskQueue();
    private final Elevator elevator;
    private final TaskRunnerStrategy taskRunnerStrategy;

    public ElevatorControllerDefaultImpl(Elevator elevator, TaskRunnerStrategy taskRunnerStrategy) {
        this.elevator = elevator;
        this.elevator.setElevatorController(this);
        this.taskRunnerStrategy = taskRunnerStrategy;
    }

    public void addTask(Task task) {
        taskQueue.addTask(task);
        log.info("Received a task: " + task.toString());
    }

    public boolean canContinue() {
        return taskQueue.hasNextTask();
    }

    public void next() {
        taskRunnerStrategy.run(this, taskQueue, elevator);
    }

    @Override
    public void moveElevatorToFloor(int floorNumber) {
        if (floorNumber < 1) {
            throw new ElevatorControllerException("Cannot go to floor number that is less than 1");
        }
        if (floorNumber > elevator.getBuilding().getNumberOfFloors()) {
            throw new ElevatorControllerException("Cannot go to floor number that is more than number of floors in the building");
        }
        while (elevator.getCurrentFloor() != floorNumber) {
            elevator.moveOneFloor(Direction.FloorComparator.compareFloors(elevator.getCurrentFloor(), floorNumber));
        }
    }
}
