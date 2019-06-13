package com.example.elevator.buttons;

import com.example.elevator.Elevator;
import com.example.elevator.tasks.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractElevatorButton implements Button {
    Elevator elevator;

    @Override
    public void press(ElevatorController elevatorController) {
    }
}
