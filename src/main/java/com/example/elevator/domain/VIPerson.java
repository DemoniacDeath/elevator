package com.example.elevator.domain;

public class VIPerson extends Person {
    private VIPerson(String name, int weight, int desiredFloorNumber) {
        super(name, weight, desiredFloorNumber);
    }

    public static VIPerson createVIPersonOnFloorWithDesiredFloor(String name, int weight, int desiredFloorNumber, Floor currentFloor) {
        VIPerson person = new VIPerson(name, weight, desiredFloorNumber);
        person.setCurrentFloor(currentFloor);
        return person;
    }
}
