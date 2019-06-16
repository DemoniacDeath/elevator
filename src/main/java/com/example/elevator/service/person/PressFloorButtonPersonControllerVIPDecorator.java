package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.domain.VIPerson;
import com.example.elevator.domain.buttons.Button;
import com.example.elevator.domain.buttons.ControlPanel;
import com.example.elevator.domain.buttons.ControlPanelVIPDecorator;
import com.example.elevator.service.Processable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PressFloorButtonPersonControllerVIPDecorator implements Processable {
    private final PressFloorButtonPersonController pressFloorButtonPersonController;

    @Override
    public boolean canContinue() {
        return pressFloorButtonPersonController.canContinue();
    }

    @Override
    public void process() {
        Person person = pressFloorButtonPersonController.getPerson();
        ControlPanel controlPanel = person.getElevator().getControlPanel();
        if (
                person instanceof VIPerson &&
                controlPanel instanceof ControlPanelVIPDecorator
        ) {
            ControlPanelVIPDecorator vipControlPanel = (ControlPanelVIPDecorator) controlPanel;
            Button button = controlPanel
                    .getFloorButton(person.getDesiredFloorNumber());
            if (button != null && button.isNotPressed()) {
                vipControlPanel.getVipControl().activateVIPMode();
                button.press(pressFloorButtonPersonController.getElevatorController().getElevatorControllerFor(
                        person.getElevator()));
            }
        } else {
            pressFloorButtonPersonController.process();
        }
    }
}
