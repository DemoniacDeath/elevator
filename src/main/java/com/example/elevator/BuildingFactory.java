package com.example.elevator;

import com.example.elevator.buttons.*;

import java.util.HashMap;
import java.util.Map;

class BuildingFactory {
    Building createBuilding(int numberOfFloors) {
        if (numberOfFloors < 1) {
            throw new ElevatorFactoryException("Cannot create elevator with less than 1 floor");
        }
        Map<Integer, CallPanel> floorCallButtons = new HashMap<>(numberOfFloors);
        Map<Integer, Button> floorButtons = new HashMap<>(numberOfFloors);
        ElevatorStopButton stopButton = new ElevatorStopButton();
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        Elevator elevator = new Elevator(controlPanel, 4, 1);
        for (int i = 1; i <= numberOfFloors; i++) {
            floorButtons.put(i, new ElevatorFloorButton(i));
            floorCallButtons.put(i, new CallPanel(
                    new ElevatorCallButton(i, Direction.UP),
                    new ElevatorCallButton(i, Direction.DOWN)
            ));
        }
        return new Building(floorCallButtons, elevator);
    }
}
