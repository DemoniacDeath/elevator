package com.example.elevator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class Person {
    @Getter
    private final int desiredFloorNumber;
    @Getter
    @Setter
    private Floor currentFloor = null;
    @Getter
    @Setter
    private Elevator elevator = null;

    public static Person createPersonOnFloorWithDesiredFloor(Floor currentFloor, int desiredFloorNumber) {
        Person person = new Person(desiredFloorNumber);
        person.setCurrentFloor(currentFloor);
        return person;
    }
}
