package com.example.elevator.service;

public class ProcessRunner {
    public static void run(Processable processable, int maximumIterations) {
        int iterationsCounter = 0;
        while (processable.canContinue()) {
            processable.process();
            if (iterationsCounter++ >= maximumIterations) {
                throw new RuntimeException("Maximum iterations count reached");
            }
        }
    }
}
