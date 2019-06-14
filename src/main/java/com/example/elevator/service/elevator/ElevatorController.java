package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.Processor;

import java.util.stream.Stream;

public interface ElevatorController extends Processor {
    void addTask(Task task);

    Stream<Elevator> getElevators();

    void stop();

    void resume();

    int getNumberOfTasks();

    ElevatorController getElevatorControllerFor(Elevator elevator);


}
