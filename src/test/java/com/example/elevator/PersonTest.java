package com.example.elevator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonTest {
    @Mock
    Elevator elevator;

    @Test void shouldEnterAndExitElevator() {
        Person person = new Person(1,1);
        person.enter(elevator);
        verify(elevator, times(1)).enter(person);
        when(elevator.getCurrentFloor()).thenReturn(3);
        person.exit(elevator);
        verify(elevator, times(1)).leave(person);
        assertEquals(3, person.getCurrentFloor());
    }
}