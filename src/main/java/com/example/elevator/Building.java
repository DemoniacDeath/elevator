package com.example.elevator;

import com.example.elevator.buttons.CallPanel;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Building {
    private Map<Integer, CallPanel> floorCallPanels;

    public int getNumberOfFloors() {
        return floorCallPanels.size();
    }

    CallPanel getCallPanelForFloor(int floor) {
        return floorCallPanels.get(floor);
    }
}
