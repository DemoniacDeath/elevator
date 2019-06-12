package com.example.elevator;

import com.example.elevator.buttons.*;
import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.ElevatorControllerImpl;

import java.util.HashMap;
import java.util.Map;

public class ElevatorFactoryImpl implements ElevatorFactory {
    public Elevator createElevator(int numberOfFloors) {
        if (numberOfFloors < 1) {
            throw new ElevatorFactoryException("Cannot create elevator with less than 1 floor");
        }
        Map<Integer, CallPanel> floorCallButtons = new HashMap<>(numberOfFloors);
        Map<Integer, Button> floorButtons = new HashMap<>(numberOfFloors);
        Building building = new BuildingImpl(floorCallButtons);
        ElevatorStopButton stopButton = new ElevatorStopButton();
        ControlPanelImpl controlPanel = new ControlPanelImpl(stopButton, floorButtons);
        ElevatorImpl elevator = new ElevatorImpl(building, controlPanel);
        ElevatorController elevatorController = new ElevatorControllerImpl(elevator);
        for (int i = 1; i <= numberOfFloors; i++) {
            floorButtons.put(i, new ElevatorFloorButton(i, elevatorController));
            floorCallButtons.put(i, new ElevatorCallPanel(
                    new ElevatorCallButton(i, Direction.UP, elevatorController),
                    new ElevatorCallButton(i, Direction.DOWN, elevatorController)
            ));
        }
        return elevator;
    }
}
