package com.example.elevator.buttons;

public interface Button {
    void press();
    void pressAnd(Runnable then);
}
