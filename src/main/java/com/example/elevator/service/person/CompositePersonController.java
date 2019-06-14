package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.elevator.ElevatorController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositePersonController extends AbstractPersonController {
    private final List<AbstractPersonController> controllers = new ArrayList<>();

    private CompositePersonController(Person person, ElevatorController elevatorController, AbstractPersonController... personControllers) {
        super(person, elevatorController);
        controllers.addAll(Arrays.asList(personControllers));
    }

    @Override
    public boolean canContinue() {
        return person.getCurrentFloor() == null ||
                person.getCurrentFloor().getFloorNumber() != person.getDesiredFloorNumber();
    }

    @Override
    public void process() {
        for (AbstractPersonController personController : controllers) {
            if (personController.canContinue()) {
                personController.process();
            }
        }
    }

    public static CompositePersonController createDefaultCompositePersonController(Person person, ElevatorController elevatorController) {
        return new CompositePersonController(person, elevatorController,
                new EnterElevatorPersonController(person, elevatorController),
                new PressFloorButtonPersonController(person, elevatorController),
                new CallElevatorPersonController(person, elevatorController),
                new LeaveElevatorPersonController(person, elevatorController)
        );
    }
}
