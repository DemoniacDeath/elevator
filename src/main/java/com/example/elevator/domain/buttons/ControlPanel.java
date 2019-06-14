package com.example.elevator.domain.buttons;

import java.util.HashMap;
import java.util.Map;

public class ControlPanel {
    private final Button stopButton;
    private final Map<Integer, Button> floorButtons = new HashMap<>();

    public ControlPanel(Button stopButton, Map<Integer, Button> floorButtons) {
        this.stopButton = stopButton;
        this.floorButtons.putAll(floorButtons);
    }

    Button getStopButton() {
        return stopButton;
    }

    public Button getFloorButton(int floor) {
        return floorButtons.get(floor);
    }
}
