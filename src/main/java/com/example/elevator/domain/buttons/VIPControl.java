package com.example.elevator.domain.buttons;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VIPControl {
    @Getter
    private boolean vipMode = false;

    public void activateVIPMode() {
        log.info("VIP mode activated");
        vipMode = true;
    }

    void deactivateVIPMode() {
        log.info("VIP mode deactivated");
        vipMode = false;
    }
}
