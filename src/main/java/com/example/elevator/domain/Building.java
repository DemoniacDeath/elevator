package com.example.elevator.domain;

import com.example.elevator.domain.buttons.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Building {
    private final Map<Integer, Floor> floors = new HashMap<>();
    @Getter
    private final Set<Elevator> elevators;

    private Building(Map<Integer, Floor> floors, Set<Elevator> elevators) {
        this.floors.putAll(floors);
        this.elevators = elevators;
        this.elevators.forEach(e -> e.setBuilding(this));
    }

    static Building createBuildingWith(int numberOfFloors, int numberOfElevators, int maximumWeight) {
        return createBuildingWith(numberOfFloors, numberOfElevators, maximumWeight, false);
    }

    public static Building createBuildingWith(int numberOfFloors, int numberOfElevators, int maximumWeight, boolean withVIP) {
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
            Map<Integer, ElevatorFloorButton> floorButtons = new HashMap<>(numberOfFloors);
            ElevatorStopButton stopButton = new ElevatorStopButton();
            for (int j = 1; j <= numberOfFloors; j++) {
                floorButtons.put(j, new DefaultElevatorFloorButton(j));
            }
            ControlPanel controlPanel;
            controlPanel = new DefaultControlPanel(stopButton, floorButtons);
            if (withVIP) {
                controlPanel = new ControlPanelVIPDecorator(controlPanel, new VIPControl());
            }
            assert floors.keySet().contains(1);
            elevators.add(new Elevator("Elevator #" + i, floors.get(1), controlPanel, 4, 1, maximumWeight));
        }
        return new Building(floors, elevators);
    }

    int getNumberOfFloors() {
        return floors.size();
    }

    public Floor getFloor(int floor) {
        return floors.get(floor);
    }

    Elevator getAvailableElevator() {
        return elevators.iterator().next();
    }
}
