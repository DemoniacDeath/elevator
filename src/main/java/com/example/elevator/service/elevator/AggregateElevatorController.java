package com.example.elevator.service.elevator;

import com.example.elevator.domain.Direction;
import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.CompositeProcessable;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
    public void acceptTask(Task task) {
        this.compositeProcessable.getProcessables().forEach(ec -> ec.acceptTask(task));
    }

    @Override
    public List<ElevatorController> getProcessables() {
        return compositeProcessable.getProcessables();
    }

    @Override
    public Set<Task> getTasksForFloorAndDirection(int currentFloorNumber, Direction direction) {
        return getProcessables().stream()
                .flatMap(p -> p.getTasksForFloorAndDirection(currentFloorNumber, direction).stream())
                .collect(Collectors.toSet());
    }
}
