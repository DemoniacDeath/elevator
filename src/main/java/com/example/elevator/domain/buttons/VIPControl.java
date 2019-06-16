package com.example.elevator.domain.buttons;

import lombok.Getter;

class VIPControl {
    @Getter
    private boolean vipMode = false;

    void activateVIPMode() {
        vipMode = true;
    }

    void deactivateVIPMode() {
        vipMode = false;
    }
}
