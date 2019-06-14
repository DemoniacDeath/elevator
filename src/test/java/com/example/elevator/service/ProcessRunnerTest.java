package com.example.elevator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessRunnerTest {

    @Mock
    Processor processor;

    @Test
    void shouldRun() {
        when(processor.canContinue()).thenReturn(true, true, false);
        ProcessRunner.run(processor, 1000);
        verify(processor, times(2)).process();
    }

    @Test
    void shouldThrowExceptionAfterReachingLimit() {
        when(processor.canContinue()).thenReturn(true, true, true, false);
        assertThrows(RuntimeException.class, () -> ProcessRunner.run(processor, 2));
    }
}