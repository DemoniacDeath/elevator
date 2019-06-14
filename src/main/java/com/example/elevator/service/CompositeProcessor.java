package com.example.elevator.service;

public interface CompositeProcessor<T> extends Processor {
    void addProcessor(T processor);
}
