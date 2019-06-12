package com.example.elevator;

import com.example.elevator.buttons.ControlPanel;
import com.example.elevator.tasks.ElevatorController;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class ElevatorImpl implements Elevator {
    private float height = 4;
    private float speed = 1;
    private Building building;
    private int currentFloor = 1;
    private Set<Person> peopleInside = new HashSet<>();
    private ControlPanel controlPanel;
    private boolean stopped = false;
    private boolean doorsOpen = false;
    private ElevatorController elevatorController;

    ElevatorImpl(Building building, ControlPanel controlPanel) {
        this.building = building;
        this.controlPanel = controlPanel;
    }

    @Override
    public void moveOneFloor(Direction direction) {
        if (doorsOpen) {
            throw new ElevatorException("Cannot move elevator while doors are open");
        }
        if (stopped) {
            throw new ElevatorException("Cannot move elevator while it's stopped");
        }
        switch(direction) {
            case UP:
                if (currentFloor == building.getNumberOfFloors()) {
                    throw new ElevatorException("Cannot move elevator up from the last floor");
                }
                this.currentFloor++;
                log.info("Moving up floor");
                break;
            case DOWN:
                if (currentFloor == 1) {
                    throw new ElevatorException("Cannot move down from the first floor");
                }
                this.currentFloor--;
                log.info("Moving down one floor");
                break;
        }
    }

    @Override
    public void enter(Person person) {
        peopleInside.add(person);
        log.info("Person entered at floor " + currentFloor);
    }

    @Override
    public void leave(Person person) {
        peopleInside.remove(person);
        log.info("Person left at floor " + currentFloor);
    }

    @Override
    public void closeDoors() {
        doorsOpen = false;
        log.info("Doors closed");
    }

    @Override
    public void openDoors() {
        doorsOpen = true;
        log.info("Doors opened");
    }

    @Override
    public void stop() {
        stopped = !stopped;
        log.info("Stop command was received");
    }

    @Override
    public Set<Person> getPeopleInside() {
        return peopleInside;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    @Override
    public Building getBuilding() {
        return building;
    }

    @Override
    public boolean areDoorsOpen() {
        return doorsOpen;
    }

    @Override
    public void setElevatorController(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    @Override
    public ElevatorController getElevatorController() {
        return elevatorController;
    }
}
