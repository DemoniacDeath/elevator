package com.example.elevator.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SimpleCompositeProcessable<T extends Processable> implements CompositeProcessable<T> {
    @Getter
    private List<T> processables = new ArrayList<>();

    @Override
    public void addProcessable(T processable) {
        processables.add(processable);
    }

    @Override
    public boolean canContinue() {
        for (T processable : processables) {
            if (processable.canContinue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void process() {
        for (T processable : processables) {
            if (processable.canContinue()) {
                processable.process();
            }
        }
    }
}
