package com.example.elevator.service;

class ProcessRunner {
    static void run(Processor processor) {
        while (processor.canContinue()) {
            processor.process();
        }
    }
}
