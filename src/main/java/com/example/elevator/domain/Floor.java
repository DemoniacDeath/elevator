package com.example.elevator.domain;

import com.example.elevator.domain.buttons.Button;
import com.example.elevator.domain.buttons.CallPanel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Floor {
    private final CallPanel callPanel;
    private final int floorNumber;

    @Override
    public String toString() {
        return "Floor #" + floorNumber;
    }

    void depressCallButtons() {
        getCallPanel().depressButtons();
    }

    public Button getCallButtonForDirection(Direction direction) {
        return getCallPanel().getButtonForDirection(direction);
    }
}
