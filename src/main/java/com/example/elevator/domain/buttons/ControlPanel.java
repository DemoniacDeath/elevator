package com.example.elevator.domain.buttons;

public interface ControlPanel {
    ElevatorStopButton getStopButton();

    ElevatorFloorButton getFloorButton(int floor);

    void depressButtonForFloor(int currentFloor);
}
