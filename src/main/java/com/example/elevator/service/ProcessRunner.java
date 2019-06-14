package com.example.elevator.service;

public class ProcessRunner {
    public static void run(Processor processor, int maximumIterations) {
        int iterationsCounter = 0;
        while (processor.canContinue()) {
            processor.process();
            if (iterationsCounter++ >= maximumIterations) {
                throw new RuntimeException("Maximum iterations count reached");
            }
        }
    }
}
