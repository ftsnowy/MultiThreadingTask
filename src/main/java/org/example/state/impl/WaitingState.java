package org.example.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.CarState;

public class WaitingState implements CarState {

    private static final Logger logger = LogManager.getLogger(WaitingState.class);

    @Override
    public void handle(String carId) {
        logger.info("{} is waiting for repair", carId);
    }

    @Override
    public CarState next() {
        return new RepairingState();
    }
}