package com.example.elevator.service;

public interface Processor {
    boolean canContinue();

    void process();
}
