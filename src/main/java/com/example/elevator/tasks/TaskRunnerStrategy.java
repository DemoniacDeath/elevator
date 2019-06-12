package com.example.elevator.tasks;

import com.example.elevator.Elevator;

interface TaskRunnerStrategy {

    void run(ElevatorController elevatorController, TaskQueue taskQueue, Elevator elevator);
}
