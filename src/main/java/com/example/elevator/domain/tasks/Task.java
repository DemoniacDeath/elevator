package com.example.elevator.domain.tasks;

import com.example.elevator.domain.Elevator;

public interface Task {
    int getFloorNumber();
    boolean isComplete(Elevator elevator);
}
