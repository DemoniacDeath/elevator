package com.example.elevator.domain.buttons;

import com.example.elevator.service.elevator.ElevatorController;

public interface Button {
    void press(ElevatorController elevatorController);

    void setPressed();

    void depress();

    boolean isNotPressed();
}
