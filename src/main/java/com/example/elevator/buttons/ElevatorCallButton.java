package com.example.elevator.buttons;

import com.example.elevator.Direction;
import com.example.elevator.tasks.CallTask;
import com.example.elevator.tasks.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCallButton extends AbstractElevatorButton {
    int floor;
    Direction direction;
    ElevatorController elevatorController;

    public void press(Runnable then) {
        elevatorController.addTask(new CallTask(floor, direction, then));
    }
}
