package com.example.elevator.buttons;

import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.MoveTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorFloorButton extends AbstractElevatorButton {
    private int floorNumber;

    @Override
    public void press(ElevatorController elevatorController) {
        elevatorController.addTask(new MoveTask(floorNumber));
    }
}
