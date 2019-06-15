package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.Processable;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractPersonController implements Processable {
    final Person person;
    final ElevatorController elevatorController;
}
