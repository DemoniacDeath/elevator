package com.example.elevator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleCompositeProcessorTest {
    @Mock
    Processor processor1;

    @Mock
    Processor processor2;

    @Test
    void shouldComposeOtherProcessors() {
        SimpleCompositeProcessor<Processor> processor = new SimpleCompositeProcessor<>();

        processor.addProcessor(processor1);
        processor.addProcessor(processor2);

        when(processor1.canContinue()).thenReturn(false);
        when(processor2.canContinue()).thenReturn(true);

        assertTrue(processor.canContinue());
        processor.process();

        verify(processor1, times(0)).process();
        verify(processor2, times(1)).process();

        clearInvocations(processor1, processor2);

        when(processor1.canContinue()).thenReturn(true);
        when(processor2.canContinue()).thenReturn(false);

        assertTrue(processor.canContinue());
        processor.process();

        verify(processor1, times(1)).process();
        verify(processor2, times(0)).process();

        clearInvocations(processor1, processor2);

        when(processor1.canContinue()).thenReturn(false);
        when(processor2.canContinue()).thenReturn(false);

        assertFalse(processor.canContinue());
        processor.process();

        verify(processor1, times(0)).process();
        verify(processor2, times(0)).process();
    }
}