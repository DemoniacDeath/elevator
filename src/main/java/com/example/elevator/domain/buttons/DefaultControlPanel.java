package com.example.elevator.domain.buttons;

import java.util.HashMap;
import java.util.Map;

public class DefaultControlPanel implements ControlPanel {
    private final ElevatorStopButton stopButton;
    private final Map<Integer, ElevatorFloorButton> floorButtons = new HashMap<>();

    public DefaultControlPanel(ElevatorStopButton stopButton, Map<Integer, ElevatorFloorButton> floorButtons) {
        this.stopButton = stopButton;
        this.floorButtons.putAll(floorButtons);
    }

    @Override
    public ElevatorStopButton getStopButton() {
        return stopButton;
    }

    @Override
    public ElevatorFloorButton getFloorButton(int floor) {
        return floorButtons.get(floor);
    }

    @Override
    public void depressButtonForFloor(int currentFloor) {
        getFloorButton(currentFloor).depress();
    }
}
