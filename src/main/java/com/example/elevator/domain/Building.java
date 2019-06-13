package com.example.elevator.domain;

import com.example.elevator.domain.buttons.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Building {
    private final Map<Integer, Floor> floors = new HashMap<>();
    private final Set<Elevator> elevators;

    private Building(Map<Integer, Floor> floors, Set<Elevator> elevators) {
        this.floors.putAll(floors);
        this.elevators = elevators;
        this.elevators.forEach(e -> e.setBuilding(this));
    }

    public static Building createBuildingWith(int numberOfFloors, int numberOfElevators) {
        if (numberOfFloors < 1) {
            throw new BuildingException("Cannot create building with less than 1 floor");
        }
        Map<Integer, Floor> floors = new HashMap<>(numberOfFloors);
        for (int i = 1; i <= numberOfFloors; i++) {
            CallPanel callPanel;
            if (i == 1 || i == numberOfFloors) {
                callPanel = new SingleButtonCallPanel(
                        new ElevatorCallButton(i, null)
                );
            } else {
                callPanel = new TwoButtonCallPanel(
                        new ElevatorCallButton(i, Direction.UP),
                        new ElevatorCallButton(i, Direction.DOWN)
                );
            }
            Floor floor = new Floor(callPanel, i);
            floors.put(i, floor);
        }
        Set<Elevator> elevators = new HashSet<>(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++) {
            Map<Integer, Button> floorButtons = new HashMap<>(numberOfFloors);
            ElevatorStopButton stopButton = new ElevatorStopButton();
            ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
            for (int j = 1; j <= numberOfFloors; j++) {
                floorButtons.put(j, new ElevatorFloorButton(j));
            }
            if (!floors.keySet().contains(1)) {
                throw new BuildingException("There is no first floor in the building");
            }
            elevators.add(new Elevator(floors.get(1), controlPanel, 4, 1));
        }
        return new Building(floors, elevators);
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

    public Floor getFloor(int floor) {
        return floors.get(floor);
    }

    public Elevator getAvailableElevator() {
        return elevators.iterator().next();
    }
}
