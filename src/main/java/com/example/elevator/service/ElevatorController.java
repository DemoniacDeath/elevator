package com.example.elevator.service;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;

public interface ElevatorController extends Processor {
    void addTask(Task task);

    Elevator getElevator();

    void moveElevatorToFloor(int floorNumber);

    void depressButtons();
}
