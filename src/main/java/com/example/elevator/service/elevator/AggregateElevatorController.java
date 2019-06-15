package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.CompositeProcessable;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AggregateElevatorController implements ElevatorController, CompositeProcessable<ElevatorController> {
    private final CompositeProcessable<ElevatorController> compositeProcessable;
    private final ElevatorControllerComparator comparator;

    @Override
    public void addTask(Task task) {
        this.getAvailableElevatorController().addTask(task);
    }

    @Override
    public Stream<Elevator> getElevators() {
        return compositeProcessable.getProcessables().stream().flatMap(ElevatorController::getElevators);
    }

    @Override
    public int getNumberOfTasks() {
        return compositeProcessable.getProcessables().stream().mapToInt(ElevatorController::getNumberOfTasks).sum();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        return compositeProcessable.getProcessables().stream()
                .map(p -> p.getElevatorControllerFor(elevator))
                .filter(Objects::nonNull)
                .findFirst().orElse(null)
                ;
    }

    private ElevatorController getAvailableElevatorController() {
        return compositeProcessable.getProcessables().stream()
                .filter(Objects::nonNull).min(comparator)
                .orElse(null)
                ;
    }

    @Override
    public boolean canContinue() {
        return this.compositeProcessable.canContinue();
    }

    @Override
    public void process() {
        this.compositeProcessable.process();
    }

    @Override
    public void addProcessable(ElevatorController processable) {
        this.compositeProcessable.addProcessable(processable);
    }

    @Override
    public List<ElevatorController> getProcessables() {
        return compositeProcessable.getProcessables();
    }
}
