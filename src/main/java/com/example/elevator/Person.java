package com.example.elevator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
class Person {
    @Getter
    @Setter
    private int currentFloor;
    @Getter
    private int desiredFloor;
}
