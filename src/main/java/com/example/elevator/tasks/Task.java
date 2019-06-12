package com.example.elevator.tasks;

public interface Task {
    int getFloorNumber();
    Runnable then();
}
