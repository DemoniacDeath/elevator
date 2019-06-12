package com.example.elevator.buttons;

import com.example.elevator.Direction;

public interface CallPanel {
    Button getButtonForDirection(Direction direction);
}
