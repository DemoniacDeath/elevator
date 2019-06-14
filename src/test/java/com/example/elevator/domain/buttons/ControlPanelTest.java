package com.example.elevator.domain.buttons;

import com.example.elevator.domain.Floor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControlPanelTest {
    @Mock
    Button stopButton;

    @Mock
    Button floor1Button;

    @Mock
    Button floor2Button;

    private Map<Integer, Button> floorButtons;

    @Mock
    Floor floor;

    @BeforeEach
    void init () {
        floorButtons = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(1, floor1Button),
                new AbstractMap.SimpleImmutableEntry<>(2, floor2Button)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Test
    void shouldGetStopButton() {
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        Button stopButton = controlPanel.getStopButton();
        assertEquals(this.stopButton, stopButton);
    }

    @Test
    void shouldGetFloorButton() {
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        assertEquals(this.floor1Button, controlPanel.getFloorButton(1));
        assertEquals(this.floor2Button, controlPanel.getFloorButton(2));
    }

    @Test
    void shouldDepressButtons() {
        ControlPanel controlPanel = new ControlPanel(stopButton, floorButtons);
        when(floor.getFloorNumber()).thenReturn(1);
        controlPanel.depressButtonForFloor(floor);
        Mockito.verify(floor1Button, Mockito.times(1)).depress();
    }
}