package com.example.elevator.service;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.TaskQueue;

interface TaskRunnerStrategy {

    void run(ElevatorController elevatorController, TaskQueue taskQueue, Elevator elevator);
}
