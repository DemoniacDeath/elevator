package com.example.elevator.buttons;

import com.example.elevator.Direction;
import com.example.elevator.tasks.CallTask;
import com.example.elevator.tasks.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCallButton extends AbstractElevatorButton {
    private final int floor;
    private final Direction direction;

    @Override
    public void press(ElevatorController elevatorController) {
        elevatorController.addTask(new CallTask(floor, direction));
    }
}
