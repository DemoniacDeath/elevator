package com.example.elevator.service;

public class ProcessRunner {
    public static void run(Processor processor) {
        while (processor.canContinue()) {
            processor.process();
        }
    }
}
