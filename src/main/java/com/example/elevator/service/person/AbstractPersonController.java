package com.example.elevator.service.person;

import com.example.elevator.domain.Person;
import com.example.elevator.service.Processor;
import com.example.elevator.service.elevator.ElevatorController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class AbstractPersonController implements Processor {
    final Person person;
    final ElevatorController elevatorController;
}
