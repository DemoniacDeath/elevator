package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.CompositeProcessor;
import com.example.elevator.service.SimpleCompositeProcessor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AggregateElevatorController implements ElevatorController, CompositeProcessor<ElevatorController> {
    private final SimpleCompositeProcessor<ElevatorController> compositeProcessor;
    private final ElevatorControllerComparator comparator;

    @Override
    public void addTask(Task task) {
        this.getAvailableElevatorController().addTask(task);
    }

    @Override
    public Stream<Elevator> getElevators() {
        return compositeProcessor.getProcessorList().stream().flatMap(ElevatorController::getElevators);
    }

    @Override
    public void stop() {
        throw new ElevatorControllerException("Cannot execute stop command on aggregate controller");
    }

    @Override
    public void resume() {
        throw new ElevatorControllerException("Cannot execute resume command on aggregate controller");
    }

    @Override
    public int getNumberOfTasks() {
        return compositeProcessor.getProcessorList().stream().mapToInt(ElevatorController::getNumberOfTasks).sum();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        return compositeProcessor.getProcessorList().stream()
                .map(p -> p.getElevatorControllerFor(elevator))
                .filter(Objects::nonNull)
                .findFirst().orElse(null)
                ;
    }

    private ElevatorController getAvailableElevatorController() {
        return compositeProcessor.getProcessorList().stream()
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
}
