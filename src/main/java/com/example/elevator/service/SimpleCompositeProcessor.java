package com.example.elevator.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SimpleCompositeProcessor<T extends Processor> implements CompositeProcessor<T> {
    @Getter
    private List<T> processors = new ArrayList<>();

    @Override
    public void addProcessor(T processor) {
        processors.add(processor);
    }

    @Override
    public boolean canContinue() {
        for (T processor : processors) {
            if (processor.canContinue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void process() {
        for (T processor : processors) {
            if (processor.canContinue()) {
                processor.process();
            }
        }
    }
}
