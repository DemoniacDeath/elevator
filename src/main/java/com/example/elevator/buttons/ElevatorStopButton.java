package com.example.elevator.buttons;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorStopButton extends AbstractElevatorButton {
    public void press(Runnable then) {
        this.elevator.stop();
    }
}
