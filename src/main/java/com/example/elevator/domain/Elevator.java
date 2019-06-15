package com.example.elevator.domain;

import com.example.elevator.domain.buttons.ControlPanel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final String name;
    private final int maximumWeight;

    @Getter
    @NonNull
    private Floor currentFloor;
    @Getter
    @Setter
    private Building building = null;
    private boolean stopped = false;
    private boolean doorsOpen = false;

    Elevator(String name,
             Floor currentFloor,
             ControlPanel controlPanel,
             int height,
             int speed,
             int maximumWeight
    ) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.controlPanel = controlPanel;
        this.height = height;
        this.speed = speed;
        this.maximumWeight = maximumWeight;
    }

    public void moveOneFloor(Direction direction) {
        if (doorsOpen) {
            throw new ElevatorException("Cannot move elevator while doors are open");
        }
        if (stopped) {
            throw new ElevatorException("Cannot move elevator while it's stopped");
        }
        if (isOverloaded()) {
            throw new ElevatorException("Cannot move elevator while overloaded");
        }
        switch (direction) {
            case UP:
                if (currentFloor.getFloorNumber() == building.getNumberOfFloors()) {
                    throw new ElevatorException("Cannot move elevator up from the last floor");
                }
                this.currentFloor = building.getFloor(this.currentFloor.getFloorNumber() + 1);
                break;
            case DOWN:
                if (currentFloor.getFloorNumber() == 1) {
                    throw new ElevatorException("Cannot move down from the first floor");
                }
                this.currentFloor = building.getFloor(this.currentFloor.getFloorNumber() - 1);
                break;
        }
        log.info(this + ": Moving " + direction + " one floor in " + calculateOnFloorMoveTime() + "s");
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
        log.info(this + ": " + currentFloor + ": " + person + " entered");
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
        log.info(this + ": " + currentFloor + ": " + person + " left");
    }

    public void closeDoors() {
        if (!doorsOpen) {
            return;
        }
        doorsOpen = false;
        log.info(this + ": " + currentFloor + ": Doors closed");
    }

    public void openDoors() {
        if (doorsOpen) {
            return;
        }
        doorsOpen = true;
        log.info(this + ": " + currentFloor + ": Doors opened");
    }

    public void stop() {
        stopped = true;
        log.info(this + ": " + currentFloor + ": Stop command was received");
    }

    public void resume() {
        stopped = false;
        log.info(this + ": " + currentFloor + ": Resume command was received");
    }

    boolean isStopped() {
        return stopped;
    }

    public boolean areDoorsOpen() {
        return doorsOpen;
    }

    public void depressFloorButton() {
        getCurrentFloor().depressCallButtons();
        getControlPanel().depressButtonForFloor(getCurrentFloor());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCurrentFloorNumber() {
        return getCurrentFloor().getFloorNumber();
    }

    public boolean isOverloaded() {
        return this.peopleInside.stream().mapToInt(Person::getWeight).sum() > maximumWeight;
    }
}
