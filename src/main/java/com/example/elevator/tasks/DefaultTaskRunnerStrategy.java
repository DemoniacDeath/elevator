package com.example.elevator.tasks;

import com.example.elevator.Elevator;

import java.util.Optional;

public class DefaultTaskRunnerStrategy implements TaskRunnerStrategy {

    @Override
    public void run(ElevatorController elevatorController, TaskQueue taskQueue, Elevator elevator) {
        Task task = taskQueue.getNextTask();
        if (task == null) {
            throw new ElevatorControllerException("No tasks left to execute");
        }

        elevatorController.moveElevatorToFloor(task.getFloorNumber());
        elevator.openDoors();
        Optional.ofNullable(task.then()).ifPresent(Runnable::run);
        elevator.closeDoors();
    }
}
