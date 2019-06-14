package com.example.elevator.service.elevator;

import java.util.Comparator;

public class ElevatorControllerComparator implements Comparator<ElevatorController> {
    @Override
    public int compare(ElevatorController e1, ElevatorController e2) {
        return e1.getNumberOfTasks() - e2.getNumberOfTasks();
    }
}
