package com.example.elevator.buttons;

import com.example.elevator.Direction;
import com.example.elevator.tasks.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCallPanel implements CallPanel {
    private Button upButton;
    private Button downButton;

    public Button getButtonForDirection(Direction direction) {
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
