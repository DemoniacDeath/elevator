package com.example.elevator.domain.buttons;

import com.example.elevator.service.ElevatorController;

public interface Button {
    void press(ElevatorController elevatorController);

    void depress();

    boolean isNotPressed();
}
