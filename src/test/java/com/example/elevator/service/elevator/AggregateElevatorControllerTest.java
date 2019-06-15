package com.example.elevator.service.elevator;

import com.example.elevator.domain.Elevator;
import com.example.elevator.domain.tasks.Task;
import com.example.elevator.service.SimpleCompositeProcessable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.MockUtil.getInvocationContainer;

@ExtendWith(MockitoExtension.class)
class AggregateElevatorControllerTest {

    @Mock
    ElevatorController elevatorController1;

    @Mock
    ElevatorController elevatorController2;

    @Mock
    Elevator elevator1;

    @Mock
    Elevator elevator2;

    @Mock
    Task task;

    @Mock
    SimpleCompositeProcessable<ElevatorController> compositeProcessable;

    @Mock
    ElevatorControllerComparator comparator;

    @Test
    void shouldAggregate() {
        AggregateElevatorController elevatorController = new AggregateElevatorController(
                compositeProcessable, comparator);
        elevatorController.addProcessable(elevatorController1);
        elevatorController.addProcessable(elevatorController2);
        verify(compositeProcessable, times(1)).addProcessable(elevatorController1);
        verify(compositeProcessable, times(1)).addProcessable(elevatorController2);
        getInvocationContainer(compositeProcessable).clearInvocations();

        when(compositeProcessable.canContinue()).thenReturn(false);
        assertFalse(elevatorController.canContinue());
        when(compositeProcessable.canContinue()).thenReturn(true);
        assertTrue(elevatorController.canContinue());

        elevatorController.process();
        verify(compositeProcessable, times(1)).process();
        getInvocationContainer(compositeProcessable).clearInvocations();

        List<ElevatorController> elevatorControllers = Arrays.asList(
                elevatorController1, elevatorController2
        );
        when(compositeProcessable.getProcessables()).thenReturn(elevatorControllers);
        assertEquals(elevatorControllers, elevatorController.getProcessables());
        lenient().when(elevatorController1.getElevatorControllerFor(elevator1)).thenReturn(elevatorController1);
        lenient().when(elevatorController1.getElevatorControllerFor(elevator2)).thenReturn(null);
        lenient().when(elevatorController2.getElevatorControllerFor(elevator1)).thenReturn(null);
        lenient().when(elevatorController2.getElevatorControllerFor(elevator2)).thenReturn(elevatorController2);

        assertEquals(elevatorController1, elevatorController.getElevatorControllerFor(elevator1));
        assertEquals(elevatorController2, elevatorController.getElevatorControllerFor(elevator2));

        when(elevatorController1.getNumberOfTasks()).thenReturn(3);
        when(elevatorController2.getNumberOfTasks()).thenReturn(2);
        assertEquals(5, elevatorController.getNumberOfTasks());

        when(elevatorController1.getElevators()).then(invocation -> Stream.of(elevator1));
        when(elevatorController2.getElevators()).then(invocation -> Stream.of(elevator2));
        when(comparator.compare(elevatorController1, elevatorController2)).thenReturn(1);
        assertTrue(elevatorController.getElevators().anyMatch(e -> e == elevator1));
        assertTrue(elevatorController.getElevators().anyMatch(e -> e == elevator2));

        elevatorController.addTask(task);
        verify(elevatorController2, times(1)).addTask(task);


    }
}