package com.example.elevator.domain.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ControlPanelVIPDecorator implements ControlPanel {
    private final ControlPanel controlPanel;
    private final VIPControl vipControl;

    @Override
    public ElevatorStopButton getStopButton() {
        return getControlPanel().getStopButton();
    }

    @Override
    public ElevatorFloorButton getFloorButton(int floor) {
        return new ElevatorFloorButtonVIPDecorator(getControlPanel().getFloorButton(floor), vipControl);
    }

    @Override
    public void depressButtonForFloor(int currentFloor) {
        getControlPanel().depressButtonForFloor(currentFloor);
    }
}
