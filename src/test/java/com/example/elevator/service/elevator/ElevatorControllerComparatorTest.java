package com.example.elevator.service.elevator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElevatorControllerComparatorTest {
    @Mock
    ElevatorController elevatorController1;

    @Mock
    ElevatorController elevatorController2;

    @Test
    void shouldCompare() {
        when(elevatorController1.getNumberOfTasks()).thenReturn(1);
        when(elevatorController2.getNumberOfTasks()).thenReturn(2);
        ElevatorControllerComparator comparator = new ElevatorControllerComparator();
        assertEquals(-1, comparator.compare(elevatorController1, elevatorController2));
    }

}