package com.example.elevator;

import com.example.elevator.buttons.CallPanel;

public interface Building {
    int getNumberOfFloors();

    CallPanel getCallPanelForFloor(int floor);
}
