package com.example.elevator.service;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.TaskQueue;
import com.example.elevator.service.ElevatorController;

interface TaskRunnerStrategy {

    void run(ElevatorController elevatorController, TaskQueue taskQueue, Elevator elevator);
}
