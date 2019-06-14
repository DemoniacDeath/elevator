package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;

public interface CallPanel {
    Button getButtonForDirection(Direction direction);

    void depressButtons();
}
