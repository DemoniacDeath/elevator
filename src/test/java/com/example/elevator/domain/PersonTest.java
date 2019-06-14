package com.example.elevator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonTest {

    @Mock
    private Floor floor;

    @Mock
    private Elevator elevator;

    @Test
    void shouldContainValues() {
        String name = "Lorem Ipsum";
        int desiredFloorNumber = 1;
        Person person = Person.createPersonOnFloorWithDesiredFloor(
                name, desiredFloorNumber, floor);
        assertNull(person.getElevator());
        assertEquals(floor, person.getCurrentFloor());
        assertEquals(name, person.getName());
        assertEquals(desiredFloorNumber, person.getDesiredFloorNumber());
        assertTrue(person.toString().contains(name));
        person.setElevator(elevator);
        person.setCurrentFloor(null);

        assertNull(person.getCurrentFloor());
        assertEquals(elevator, person.getElevator());
    }

}