package com.example.elevator;

import com.example.elevator.buttons.ControlPanel;
import com.example.elevator.tasks.ElevatorController;

import java.util.Set;

public interface Elevator {

    void moveOneFloor(Direction direction);

    int getCurrentFloor();

    Set<Person> getPeopleInside();

    void enter(Person person);

    void leave(Person person);

    ControlPanel getControlPanel();

    Building getBuilding();

    void stop();

    void openDoors();

    void closeDoors();

    boolean areDoorsOpen();

    void setElevatorController(ElevatorController elevatorController);

    ElevatorController getElevatorController();
}
