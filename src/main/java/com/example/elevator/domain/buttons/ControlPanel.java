package com.example.elevator.domain.buttons;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ControlPanel {
    private Button stopButton;
    private Map<Integer, Button> floorButtons;

    public Button getStopButton() {
        return stopButton;
    }

    public Button getFloorButton(int floor) {
        return floorButtons.get(floor);
    }
}
