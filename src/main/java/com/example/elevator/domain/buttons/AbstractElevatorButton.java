package com.example.elevator.domain.buttons;

import com.example.elevator.service.elevator.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractElevatorButton implements Button {
    private boolean pressed = false;

    @Override
    public void press(ElevatorController elevatorController) {
        pressed = true;
    }

    @Override
    public boolean isNotPressed() {
        return !pressed;
    }

    @Override
    public void depress() {
        pressed = false;
    }
}
