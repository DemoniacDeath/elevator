package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositePersonController extends AbstractPersonController {
    @Getter
    private final List<AbstractPersonController> controllers = new ArrayList<>();

    CompositePersonController(Person person, ElevatorController elevatorController, AbstractPersonController... personControllers) {
        super(person, elevatorController);
        controllers.addAll(Arrays.asList(personControllers));
    }

    public static CompositePersonController createDefaultPersonController(ElevatorController elevatorController, Person person) {
        return new CompositePersonController(person, elevatorController,
                new EnterElevatorPersonController(person, elevatorController),
                new CallElevatorPersonController(person, elevatorController),
                new OverloadPreventionPersonController(person, elevatorController),
                new PressFloorButtonPersonController(person, elevatorController),
                new LeaveElevatorPersonController(person, elevatorController)
        );
    }

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() == null ||
                person.getCurrentFloorNumber() != person.getDesiredFloorNumber();
    }

    @Override
    public void process() {
        for (AbstractPersonController personController : controllers) {
            if (personController.canContinue()) {
                personController.process();
            }
        }
    }
}
