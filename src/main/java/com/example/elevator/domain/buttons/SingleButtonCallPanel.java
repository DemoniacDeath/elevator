package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Direction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleButtonCallPanel implements CallPanel {
    private final Button callButton;

    @Override
    public Button getButtonForDirection(Direction direction) {
        return callButton;
    }

    @Override
    public void depressButtons() {
        callButton.depress();
    }
}
