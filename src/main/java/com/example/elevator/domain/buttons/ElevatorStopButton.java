package com.example.elevator.domain.buttons;

import com.example.elevator.service.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorStopButton extends AbstractElevatorButton {
    public void press(ElevatorController elevatorController) {
        if (isNotPressed()) {
            elevatorController.getElevator().stop();
            super.press(elevatorController);
        } else {
            elevatorController.getElevator().resume();
            this.depress();
        }
    }
}
