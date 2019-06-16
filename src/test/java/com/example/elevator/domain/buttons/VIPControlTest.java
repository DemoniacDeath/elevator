package com.example.elevator.domain.buttons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VIPControlTest {
    @Test
    void shouldHoldActivationMode() {
        VIPControl vipControl = new VIPControl();
        assertFalse(vipControl.isVipMode());
        vipControl.activateVIPMode();
        assertTrue(vipControl.isVipMode());
        vipControl.deactivateVIPMode();
        assertFalse(vipControl.isVipMode());
    }
}