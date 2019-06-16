package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.Processable;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
abstract class AbstractPersonController implements Processable {
    private final Person person;
    private final ElevatorController elevatorController;
}
