package com.example.elevator.buttons;

public interface ControlPanel {
    Button getStopButton();
    Button getFloorButton(int floor);
}
