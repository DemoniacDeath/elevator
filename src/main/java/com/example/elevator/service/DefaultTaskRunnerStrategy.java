package com.example.elevator.service;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.domain.tasks.TaskQueue;

public class DefaultTaskRunnerStrategy implements TaskRunnerStrategy {

    @Override
    public void run(ElevatorController elevatorController, TaskQueue taskQueue, Elevator elevator) {
        elevator.closeDoors();
        Task task = taskQueue.getNextTask();
        if (task == null) {
            return;
        }
        elevatorController.moveElevatorToFloor(task.getFloorNumber());
        elevator.openDoors();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.UP).depress();
        elevator.getCurrentFloor().getCallPanel().getButtonForDirection(Direction.DOWN).depress();
        elevator.getControlPanel().getFloorButton(elevator.getCurrentFloor().getFloorNumber()).depress();    }
}
