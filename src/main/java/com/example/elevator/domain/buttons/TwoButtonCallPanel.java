package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TwoButtonCallPanel implements CallPanel {
    private final Button upButton;
    private final Button downButton;

    @Override
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
                throw new RuntimeException("Unknown value of Direction " + direction);
        }
    }

    @Override
    public void depressButtons() {
        upButton.depress();
        downButton.depress();
    }
}
