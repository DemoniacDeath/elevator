package com.example.elevator;

import com.example.elevator.buttons.*;
import com.example.elevator.tasks.DefaultTaskRunnerStrategy;
import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.ElevatorControllerDefaultImpl;

import java.util.HashMap;
import java.util.Map;

class ElevatorFactory {
    Elevator createElevator(int numberOfFloors) {
        if (numberOfFloors < 1) {
            throw new ElevatorFactoryException("Cannot create elevator with less than 1 floor");
        }
        Map<Integer, CallPanel> floorCallButtons = new HashMap<>(numberOfFloors);
        Map<Integer, Button> floorButtons = new HashMap<>(numberOfFloors);
        Building building = new Building(floorCallButtons);
        ElevatorStopButton stopButton = new ElevatorStopButton();
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        Elevator elevator = new Elevator(building, controlPanel);
        ElevatorController elevatorController = new ElevatorControllerDefaultImpl(elevator, new DefaultTaskRunnerStrategy());
        for (int i = 1; i <= numberOfFloors; i++) {
            floorButtons.put(i, new ElevatorFloorButton(i, elevatorController));
            floorCallButtons.put(i, new CallPanel(
                    new ElevatorCallButton(i, Direction.UP, elevatorController),
                    new ElevatorCallButton(i, Direction.DOWN, elevatorController)
            ));
        }
        return elevator;
    }
}
