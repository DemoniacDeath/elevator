package com.example.elevator;

import com.example.elevator.buttons.Button;
import com.example.elevator.buttons.CallPanel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class BuildingImpl implements Building {
    private Map<Integer, CallPanel> floorCallPanels;

    public int getNumberOfFloors() {
        return floorCallPanels.size();
    }

    public CallPanel getCallPanelForFloor(int floor) {
        return floorCallPanels.get(floor);
    }
}
