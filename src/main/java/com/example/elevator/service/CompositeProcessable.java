package com.example.elevator.service;

import java.util.List;

public interface CompositeProcessable<T> extends Processable {
    void addProcessable(T processable);

    List<T> getProcessables();
}
