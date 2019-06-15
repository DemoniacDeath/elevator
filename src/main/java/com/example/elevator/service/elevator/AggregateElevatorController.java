package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.CompositeProcessor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AggregateElevatorController implements ElevatorController, CompositeProcessor<ElevatorController> {
    private final CompositeProcessor<ElevatorController> compositeProcessor;
    private final ElevatorControllerComparator comparator;

    @Override
    public void addTask(Task task) {
        this.getAvailableElevatorController().addTask(task);
    }

    @Override
    public Stream<Elevator> getElevators() {
        return compositeProcessor.getProcessors().stream().flatMap(ElevatorController::getElevators);
    }

    @Override
    public int getNumberOfTasks() {
        return compositeProcessor.getProcessors().stream().mapToInt(ElevatorController::getNumberOfTasks).sum();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        return compositeProcessor.getProcessors().stream()
                .map(p -> p.getElevatorControllerFor(elevator))
                .filter(Objects::nonNull)
                .findFirst().orElse(null)
                ;
    }

    private ElevatorController getAvailableElevatorController() {
        return compositeProcessor.getProcessors().stream()
                .filter(Objects::nonNull).min(comparator)
                .orElse(null)
                ;
    }

    @Override
    public boolean canContinue() {
        return this.compositeProcessor.canContinue();
    }

    @Override
    public void process() {
        this.compositeProcessor.process();
    }

    @Override
    public void addProcessor(ElevatorController processor) {
        this.compositeProcessor.addProcessor(processor);
    }

    @Override
    public List<ElevatorController> getProcessors() {
        return compositeProcessor.getProcessors();
    }
}
