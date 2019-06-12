package com.example.elevator.buttons;

import com.example.elevator.Direction;
import com.example.elevator.tasks.CallTask;
import com.example.elevator.tasks.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCallButton extends AbstractElevatorButton {
    private final int floor;
    private final Direction direction;
    private final ElevatorController elevatorController;

    @Override
    public void press() {
        elevatorController.addTask(new CallTask(floor, direction));
    }

    public void pressAnd(Runnable then) {
        elevatorController.addTask(new CallTask(floor, direction, then));
    }
}
