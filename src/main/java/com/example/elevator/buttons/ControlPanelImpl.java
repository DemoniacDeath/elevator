package com.example.elevator.buttons;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ControlPanelImpl implements ControlPanel {
    private Button stopButton;
    private Map<Integer, Button> floorButtons;

    public Button getStopButton() {
        return stopButton;
    }

    public Button getFloorButton(int floor) {
        return floorButtons.get(floor);
    }
}