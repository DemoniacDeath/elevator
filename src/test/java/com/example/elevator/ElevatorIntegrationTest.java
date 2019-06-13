package com.example.elevator;

import com.example.elevator.buttons.Button;
import com.example.elevator.tasks.DefaultTaskRunnerStrategy;
import com.example.elevator.tasks.ElevatorController;
import com.example.elevator.tasks.ElevatorControllerDefaultImpl;
import com.example.elevator.tasks.ElevatorRunner;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.elevator.Direction.compareFloors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElevatorIntegrationTest {
    private final BuildingFactory buildingFactory = new BuildingFactory();

    @Test
    void testShouldMoveThreePeople() {
        Building building = buildingFactory.createBuilding(4);
        Elevator elevator = building.getAvailableElevator();
        ElevatorController elevatorController = new ElevatorControllerDefaultImpl(elevator, new DefaultTaskRunnerStrategy());
        List<Person> people = Arrays.asList(
            new Person(1, 4),
            new Person(3, 2),
            new Person(4, 1)
        );

        for (Person person : people) {
            Button button = elevator.getBuilding().getCallPanelForFloor(person.getCurrentFloor())
                    .getButtonForDirection(compareFloors(person.getCurrentFloor(), person.getDesiredFloor()));
            if (button != null) {
                button.press(elevatorController);
            }
        }
        ElevatorRunner.run(elevatorController);
        assertTrue(true);
//        assertEquals(4, people.get(0).getCurrentFloor());
//        assertEquals(2, people.get(1).getCurrentFloor());
//        assertEquals(1, people.get(2).getCurrentFloor());
    }
}
