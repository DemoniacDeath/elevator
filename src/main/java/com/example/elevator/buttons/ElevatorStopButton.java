package com.example.elevator.buttons;

import com.example.elevator.tasks.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorStopButton extends AbstractElevatorButton {
    public void press(ElevatorController elevatorController) {
        this.elevator.stop();
    }
}
