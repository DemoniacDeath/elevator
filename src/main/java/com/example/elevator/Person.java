package com.example.elevator;

public interface Person {
    int getDesiredFloor();

    int getCurrentFloor();

    void getToTheDesiredFloor(Elevator elevator);

    void enter(Elevator elevator);

    void exit(Elevator elevator);
}
