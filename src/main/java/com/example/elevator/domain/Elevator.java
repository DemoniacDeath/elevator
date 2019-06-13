package com.example.elevator.domain;

import com.example.elevator.domain.buttons.ControlPanel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class Elevator {
    @Getter
    @NonNull
    private final Set<Person> peopleInside = new HashSet<>();
    @Getter
    @NonNull
    private final ControlPanel controlPanel;
    private final int height;
    private final int speed;

    @Getter
    @Setter
    @NonNull
    private Floor currentFloor;
    @Getter
    @Setter
    private Building building = null;
    private boolean stopped = false;
    private boolean doorsOpen = false;

    Elevator(Floor currentFloor, ControlPanel controlPanel, int height, int speed) {
        this.currentFloor = currentFloor;
        this.controlPanel = controlPanel;
        this.height = height;
        this.speed = speed;
    }

    public void moveOneFloor(Direction direction) {
        if (doorsOpen) {
            throw new ElevatorException("Cannot move elevator while doors are open");
        }
        if (stopped) {
            throw new ElevatorException("Cannot move elevator while it's stopped");
        }
        switch (direction) {
            case UP:
                if (currentFloor.getFloorNumber() == building.getNumberOfFloors()) {
                    throw new ElevatorException("Cannot move elevator up from the last floor");
                }
                this.currentFloor = building.getFloor(this.currentFloor.getFloorNumber() + 1);
                log.info("Moving up one floor in " + calculateOnFloorMoveTime() + "s");
                break;
            case DOWN:
                if (currentFloor.getFloorNumber() == 1) {
                    throw new ElevatorException("Cannot move down from the first floor");
                }
                this.currentFloor = building.getFloor(this.currentFloor.getFloorNumber() - 1);
                log.info("Moving down one floor in " + calculateOnFloorMoveTime() + "s");
                break;
        }
    }

    private int calculateOnFloorMoveTime() {
        return this.height * this.speed;
    }

    public void enter(Person person) {
        if (!doorsOpen) {
            throw new ElevatorException("Cannot allow a person to enter when doors are closed");
        }
        peopleInside.add(person);
        person.setCurrentFloor(null);
        person.setElevator(this);
        log.info(person + " entered on " + currentFloor.toString());
    }

    public void leave(Person person) {
        if (!doorsOpen) {
            throw new ElevatorException("Cannot allow a person to leave when doors are closed");
        }
        if (!peopleInside.remove(person)) {
            throw new ElevatorException("Cannot make a person leave if the person is not inside elevator");
        }
        person.setCurrentFloor(currentFloor);
        person.setElevator(null);
        log.info(person + " left on " + currentFloor.toString());
    }

    public void closeDoors() {
        if (!doorsOpen) {
            return;
        }
        doorsOpen = false;
        log.info("Doors closed on " + currentFloor.toString());
    }

    public void openDoors() {
        if (doorsOpen) {
            return;
        }
        doorsOpen = true;
        log.info("Doors opened on " + currentFloor.toString());
    }

    public void stop() {
        stopped = true;
        log.info("Stop command was received");
    }

    public void resume() {
        stopped = false;
        log.info("Resume command was received");
    }

    boolean isStopped() {
        return stopped;
    }

    public boolean areDoorsOpen() {
        return doorsOpen;
    }
}
