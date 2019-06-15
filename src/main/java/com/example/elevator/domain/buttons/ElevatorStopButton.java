package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Elevator;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorStopButton extends AbstractElevatorButton {
    public void press(ElevatorController elevatorController) {
        if (isNotPressed()) {
            elevatorController.getElevators().forEach(Elevator::stop);
            setPressed();
        } else {
            elevatorController.getElevators().forEach(Elevator::resume);
            depress();
        }
    }
}
