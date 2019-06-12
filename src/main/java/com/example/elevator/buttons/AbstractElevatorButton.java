package com.example.elevator.buttons;

import com.example.elevator.Elevator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractElevatorButton implements Button {
    Elevator elevator;
}
