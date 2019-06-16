package com.example.elevator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VIPersonTest {
    @Mock
    Floor floor;

    @Test
    void shouldCreateVIPerson() {
        VIPerson viPerson = VIPerson.createVIPersonOnFloorWithDesiredFloor(
                "Alice", 70, 12, floor);

        assertNotNull(viPerson);


    }
}