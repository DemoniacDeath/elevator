package com.example.elevator.tasks;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorRunner {
    public static void run(ElevatorController elevatorController) {
        while (elevatorController.canContinue()) {
            elevatorController.next();
        }
    }
}
