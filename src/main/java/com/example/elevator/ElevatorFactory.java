package com.example.elevator;

public interface ElevatorFactory {
    Elevator createElevator(int numberOfFloors);
}
