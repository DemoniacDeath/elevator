package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallPanel {
    private Button upButton;
    private Button downButton;

    public Button getButtonForDirection(Direction direction) {
        if (direction == null) {
            return null;
        }
        switch (direction) {
            case UP:
                return upButton;
            case DOWN:
                return downButton;
            default:
                return null;
        }
    }
}
