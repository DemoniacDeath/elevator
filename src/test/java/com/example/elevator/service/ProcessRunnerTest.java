package com.example.elevator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessRunnerTest {

    @Mock
    Processable processable;

    @Test
    void shouldRun() {
        when(processable.canContinue()).thenReturn(true, true, false);
        ProcessRunner.run(processable, 1000);
        verify(processable, times(2)).process();
    }

    @Test
    void shouldThrowExceptionAfterReachingLimit() {
        when(processable.canContinue()).thenReturn(true, true, true, false);
        assertThrows(RuntimeException.class, () -> ProcessRunner.run(processable, 2));
    }
}