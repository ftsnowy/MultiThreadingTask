package org.example.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.CarState;

public class CompletedState implements CarState {

    private static final Logger logger = LogManager.getLogger(CompletedState.class);

    @Override
    public void handle(String carId) {
        logger.info("{} completed the repair and left the service", carId);
    }
}