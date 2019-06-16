package com.example.elevator.domain.buttons;

import com.example.elevator.domain.tasks.VIPMoveTask;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorFloorButtonVIPDecorator extends AbstractElevatorButton {
    private final ElevatorFloorButton elevatorFloorButton;
    private final VIPControl vipControl;

    @Override
    public void press(ElevatorController elevatorController) {
        if (vipControl.isVipMode()) {
            if (isNotPressed()) {
                elevatorController.addTask(new VIPMoveTask(elevatorFloorButton.getFloorNumber()));
                setPressed();
            }
        } else {
            elevatorFloorButton.press(elevatorController);
        }
    }

    @Override
    void setPressed() {
        elevatorFloorButton.setPressed();
    }

    @Override
    public boolean isNotPressed() {
        return elevatorFloorButton.isNotPressed();
    }

    @Override
    public void depress() {
        elevatorFloorButton.depress();
    }
}
