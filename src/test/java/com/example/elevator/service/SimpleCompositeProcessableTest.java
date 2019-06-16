package com.example.elevator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleCompositeProcessableTest {
    @Mock
    Processable processable1;

    @Mock
    Processable processable2;

    @Test
    void shouldComposeOtherProcessables() {
        SimpleCompositeProcessable<Processable> processable = new SimpleCompositeProcessable<>();

        processable.addProcessable(processable1);
        processable.addProcessable(processable2);

        when(processable1.canContinue()).thenReturn(false);
        when(processable2.canContinue()).thenReturn(true);

        assertTrue(processable.canContinue());
        processable.process();

        verify(processable1, times(0)).process();
        verify(processable2, times(1)).process();

        clearInvocations(processable1, processable2);

        when(processable1.canContinue()).thenReturn(true);
        when(processable2.canContinue()).thenReturn(false);

        assertTrue(processable.canContinue());
        processable.process();

        verify(processable1, times(1)).process();
        verify(processable2, times(0)).process();

        clearInvocations(processable1, processable2);

        when(processable1.canContinue()).thenReturn(false);
        when(processable2.canContinue()).thenReturn(false);

        assertFalse(processable.canContinue());
        processable.process();

        verify(processable1, times(0)).process();
        verify(processable2, times(0)).process();
    }
}