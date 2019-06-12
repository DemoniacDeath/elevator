package com.example.elevator.tasks;

interface Task {
    int getFloorNumber();
    Runnable then();
}
