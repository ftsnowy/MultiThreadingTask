package org.example.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.CarState;

public class RepairingState implements CarState {

    private static final Logger logger = LogManager.getLogger(RepairingState.class);

    @Override
    public void handle(String carId) {
        logger.info("{} is being repaired", carId);
    }
}