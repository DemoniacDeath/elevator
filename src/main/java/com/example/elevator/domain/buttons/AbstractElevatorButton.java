package com.example.elevator.domain.buttons;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractElevatorButton implements Button {
    private boolean pressed = false;

    @Override
    public void setPressed() {
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
