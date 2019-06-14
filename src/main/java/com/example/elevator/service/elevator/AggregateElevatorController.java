package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.CompositeProcessor;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AggregateElevatorController extends CompositeProcessor<ElevatorController> implements ElevatorController {
    @Override
    public void addTask(Task task) {
        this.getAvailableElevatorController().addTask(task);
    }

    @Override
    public Stream<Elevator> getElevators() {
        return this.getProcessorList().stream().flatMap(ElevatorController::getElevators);
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
        return this.getProcessorList().stream().mapToInt(ElevatorController::getNumberOfTasks).sum();
    }

    @Override
    public ElevatorController getElevatorControllerFor(Elevator elevator) {
        return this.getProcessorList().stream()
                .map(p -> p.getElevatorControllerFor(elevator))
                .filter(Objects::nonNull)
                .findFirst().orElse(null)
                ;
    }

    private ElevatorController getAvailableElevatorController() {
        return this.getProcessorList().stream()
                .filter(Objects::nonNull).min(new ElevatorControllerComparator())
                .orElse(null)
                ;
    }
}
