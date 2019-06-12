package com.example.elevator.tasks;

import com.example.elevator.Direction;
import com.example.elevator.Elevator;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElevatorControllerImpl implements ElevatorController {
    private TaskQueue taskQueue = new TaskQueueImpl();
    private Elevator elevator;

    public ElevatorControllerImpl(Elevator elevator) {
        this.elevator = elevator;
        this.elevator.setElevatorController(this);
    }

    public void addTask(Task task) {
        taskQueue.addTask(task);
        log.info("Received a task: " + task.toString());
    }

    public boolean canContinue() {
        return taskQueue.hasNextTask();
    }

    public void next() {
        Task task = taskQueue.getNextTask();
        if (task == null) {
            throw new ElevatorControllerException("No tasks left to execute");
        }
        this.moveElevatorToFloor(task.getFloorNumber());
        elevator.openDoors();
        task.then().run();
        elevator.closeDoors();
    }

    private void moveElevatorToFloor(int floorNumber) {
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
