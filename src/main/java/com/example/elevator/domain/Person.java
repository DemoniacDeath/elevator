package com.example.elevator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class Person {
    @Getter
    private final String name;
    @Getter
    private final int desiredFloorNumber;
    @Getter
    @Setter
    private Floor currentFloor = null;
    @Getter
    @Setter
    private Elevator elevator = null;

    public static Person createPersonOnFloorWithDesiredFloor(String name, int desiredFloorNumber, Floor currentFloor) {
        Person person = new Person(name, desiredFloorNumber);
        person.setCurrentFloor(currentFloor);
        return person;
    }

    @Override
    public String toString() {
        return name;
    }
}
