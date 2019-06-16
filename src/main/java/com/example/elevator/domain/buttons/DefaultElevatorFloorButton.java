package com.example.elevator.domain.buttons;

import com.example.elevator.domain.tasks.MoveTask;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DefaultElevatorFloorButton extends AbstractElevatorButton implements ElevatorFloorButton {
    @Getter(onMethod_ = {@Override})
    private int floorNumber;

    @Override
    public void press(ElevatorController elevatorController) {
        if (isNotPressed()) {
            elevatorController.addTask(new MoveTask(floorNumber));
            setPressed();
        }
    }
}
