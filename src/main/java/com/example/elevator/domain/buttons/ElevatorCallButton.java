package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.tasks.CallTask;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCallButton extends AbstractElevatorButton {
    private final int floor;
    private final Direction direction;

    @Override
    public void press(ElevatorController elevatorController) {
        if (isNotPressed()) {
            elevatorController.addTask(new CallTask(floor, direction));
            super.press(elevatorController);
        }
    }
}
