package com.example.elevator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeProcessor implements Processor {
    private List<Processor> processorList = new ArrayList<>();

    public CompositeProcessor(Processor... processors) {
        processorList.addAll(Arrays.asList(processors));
    }

    public void addProcessor(Processor processor) {
        processorList.add(processor);
    }

    @Override
    public boolean canContinue() {
        for (Processor processor : processorList) {
            if (processor.canContinue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void process() {
        for (Processor processor : processorList) {
            if (processor.canContinue()) {
                processor.process();
            }
        }
    }
}
