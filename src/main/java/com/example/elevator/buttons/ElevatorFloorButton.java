package com.example.elevator.buttons;

import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.MoveTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorFloorButton extends AbstractElevatorButton {
    int floorNumber;
    ElevatorController elevatorController;
    public void press(Runnable then) {
        elevatorController.addTask(new MoveTask(floorNumber, then));
    }
}
