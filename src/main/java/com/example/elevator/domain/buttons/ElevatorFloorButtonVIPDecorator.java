package com.example.elevator.domain.buttons;

import com.example.elevator.domain.tasks.VIPMoveTask;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElevatorFloorButtonVIPDecorator extends AbstractElevatorButton implements ElevatorFloorButton {
    private final ElevatorFloorButton elevatorFloorButton;
    @Getter
    private final VIPControl vipControl;

    @Override
    public void press(ElevatorController elevatorController) {
        if (vipControl.isVipMode()) {
            if (isNotPressed()) {
                elevatorController.addTask(new VIPMoveTask(elevatorFloorButton.getFloorNumber()));
                setPressed();
            }
            vipControl.deactivateVIPMode();
        } else {
            elevatorFloorButton.press(elevatorController);
        }
    }

    @Override
    public void setPressed() {
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

    @Override
    public int getFloorNumber() {
        return elevatorFloorButton.getFloorNumber();
    }
}
