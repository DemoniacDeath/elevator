package com.example.elevator.service;

import java.util.List;

public interface CompositeProcessor<T> extends Processor {
    void addProcessor(T processor);
    List<T> getProcessors();
}
