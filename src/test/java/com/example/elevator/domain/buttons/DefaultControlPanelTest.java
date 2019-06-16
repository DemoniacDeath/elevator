package com.example.elevator.domain.buttons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DefaultControlPanelTest {
    @Mock
    ElevatorStopButton stopButton;

    @Mock
    ElevatorFloorButton floor1Button;

    @Mock
    ElevatorFloorButton floor2Button;

    private Map<Integer, ElevatorFloorButton> floorButtons;

    @BeforeEach
    void init() {
        floorButtons = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(1, floor1Button),
                new AbstractMap.SimpleImmutableEntry<>(2, floor2Button)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Test
    void shouldGetStopButton() {
        DefaultControlPanel controlPanel = new DefaultControlPanel(stopButton, floorButtons);
        Button stopButton = controlPanel.getStopButton();
        assertEquals(this.stopButton, stopButton);
    }

    @Test
    void shouldGetFloorButton() {
        DefaultControlPanel controlPanel = new DefaultControlPanel(stopButton, floorButtons);
        assertEquals(this.floor1Button, controlPanel.getFloorButton(1));
        assertEquals(this.floor2Button, controlPanel.getFloorButton(2));
    }

    @Test
    void shouldDepressButtons() {
        DefaultControlPanel controlPanel = new DefaultControlPanel(stopButton, floorButtons);
        controlPanel.depressButtonForFloor(1);
        Mockito.verify(floor1Button, Mockito.times(1)).depress();
    }
}