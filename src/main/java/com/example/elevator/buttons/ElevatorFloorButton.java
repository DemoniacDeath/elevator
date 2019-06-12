package com.example.elevator.buttons;

import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.MoveTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorFloorButton extends AbstractElevatorButton {
    private int floorNumber;
    private final ElevatorController elevatorController;

    @Override
    public void press() {
        elevatorController.addTask(new MoveTask(floorNumber));
    }

    public void pressAnd(Runnable then) {
        elevatorController.addTask(new MoveTask(floorNumber, then));
    }
}
