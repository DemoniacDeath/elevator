package com.example.elevator;

import com.example.elevator.buttons.CallPanel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

public class Building {
    private final Map<Integer, CallPanel> floorCallPanels;
    private final Elevator elevator;

    Building(Map<Integer, CallPanel> floorCallPanels, Elevator elevator) {
        this.floorCallPanels = floorCallPanels;
        this.elevator = elevator;
        this.elevator.setBuilding(this);
    }

    public int getNumberOfFloors() {
        return floorCallPanels.size();
    }

    CallPanel getCallPanelForFloor(int floor) {
        return floorCallPanels.get(floor);
    }

    Elevator getAvailableElevator() {
        return elevator;
    }
}
