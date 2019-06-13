package com.example.elevator;

import com.example.elevator.buttons.ControlPanel;
import com.example.elevator.tasks.ElevatorController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class Elevator {
    @Getter
    private final Set<Person> peopleInside = new HashSet<>();
    @Getter
    private final ControlPanel controlPanel;
    private final int height;
    private final int speed;

    @Getter
    private int currentFloor = 1;
    @Getter
    @Setter
    private Building building;
    private boolean stopped = false;
    private boolean doorsOpen = false;

    Elevator(ControlPanel controlPanel, int height, int speed) {
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
                if (currentFloor == building.getNumberOfFloors()) {
                    throw new ElevatorException("Cannot move elevator up from the last floor");
                }
                this.currentFloor++;
                log.info("Moving up one floor in " + calculateOnFloorMoveTime() + "s");
                break;
            case DOWN:
                if (currentFloor == 1) {
                    throw new ElevatorException("Cannot move down from the first floor");
                }
                this.currentFloor--;
                log.info("Moving down one floor in " + calculateOnFloorMoveTime() + "s");
                break;
        }
    }

    private int calculateOnFloorMoveTime() {
        return this.height * this.speed;
    }

    void enter(Person person) {
        if (!doorsOpen) {
            throw new ElevatorException("Cannot allow a person to enter when doors are closed");
        }
        peopleInside.add(person);
        log.info("Person entered on floor #" + currentFloor);
    }

    void leave(Person person) {
        if (!doorsOpen) {
            throw new ElevatorException("Cannot allow a person to leave when doors are closed");
        }
        if (!peopleInside.remove(person)) {
            throw new ElevatorException("Cannot make a person leave if the person is not inside elevator");
        }
        person.setCurrentFloor(currentFloor);
        log.info("Person left on floor #" + currentFloor);
    }

    public void closeDoors() {
        doorsOpen = false;
        log.info("Doors closed on floor #" + currentFloor);
    }

    public void openDoors() {
        doorsOpen = true;
        log.info("Doors opened on floor #" + currentFloor);
    }

    public void stop() {
        stopped = !stopped;
        log.info("Stop command was received");
    }

    boolean isStopped() {
        return stopped;
    }

    boolean areDoorsOpen() {
        return doorsOpen;
    }
}
