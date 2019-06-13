package com.example.elevator.domain;

import com.example.elevator.domain.buttons.*;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class Building {
    private final Map<Integer, Floor> floors = new HashMap<>();
    private final Elevator elevator;

    private Building(Map<Integer, Floor> floors, Elevator elevator) {
        this.floors.putAll(floors);
        this.elevator = elevator;
        this.elevator.setBuilding(this);
    }

    public static Building createBuildingWith(int numberOfFloors) {
        if (numberOfFloors < 1) {
            throw new BuildingException("Cannot create building with less than 1 floor");
        }
        Map<Integer, Floor> floors = new HashMap<>(numberOfFloors);
        Map<Integer, Button> floorButtons = new HashMap<>(numberOfFloors);
        ElevatorStopButton stopButton = new ElevatorStopButton();
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        for (int i = 1; i <= numberOfFloors; i++) {
            floorButtons.put(i, new ElevatorFloorButton(i));
            Floor floor = new Floor(new CallPanel(
                    new ElevatorCallButton(i, Direction.UP),
                    new ElevatorCallButton(i, Direction.DOWN)
            ), i);
            floors.put(i, floor);
        }
        if (!floors.keySet().contains(1)) {
            throw new BuildingException("There is no first floor in the building");
        }
        Elevator elevator = new Elevator(floors.get(1), controlPanel, 4, 1);
        return new Building(floors, elevator);
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

    public Floor getFloor(int floor) {
        return floors.get(floor);
    }

    public Elevator getAvailableElevator() {
        return elevator;
    }
}
