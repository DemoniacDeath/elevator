package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.Processor;

public interface ElevatorController extends Processor {
    void addTask(Task task);

    Elevator getElevator();

    void stop();

    void resume();
}
