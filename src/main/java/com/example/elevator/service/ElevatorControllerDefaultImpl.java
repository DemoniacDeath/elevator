package com.example.elevator.service;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ElevatorControllerDefaultImpl implements ElevatorController {
    private final TaskQueue taskQueue;
    @Getter
    private final Elevator elevator;

    public void addTask(Task task) {
        taskQueue.addTask(task);
        log.info("Received a task: " + task.toString());
    }

    public boolean canContinue() {
        return taskQueue.hasNextTaskForCurrentFloor(elevator.getCurrentFloor().getFloorNumber());
    }

    public void process() {
        elevator.closeDoors();

        Task task = taskQueue.getNextTaskForCurrentFloor(elevator.getCurrentFloor().getFloorNumber());
        if (task == null) {
            return;
        }
        moveElevatorToFloor(task.getFloorNumber());
        elevator.openDoors();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.UP).depress();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.DOWN).depress();
        elevator.getControlPanel().getFloorButton(elevator.getCurrentFloor().getFloorNumber()).depress();
    }

    @Override
    public void moveElevatorToFloor(int floorNumber) {
        if (floorNumber < 1) {
            throw new ElevatorControllerException("Cannot go to floor number that is less than 1");
        }
        if (floorNumber > elevator.getBuilding().getNumberOfFloors()) {
            throw new ElevatorControllerException("Cannot go to floor number that is more than number of floors in the building");
        }
        while (elevator.getCurrentFloor().getFloorNumber() != floorNumber) {
            elevator.moveOneFloor(Direction.compareFloors(elevator.getCurrentFloor().getFloorNumber(), floorNumber));
        }
    }
}
