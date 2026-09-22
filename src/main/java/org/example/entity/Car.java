package org.example.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.CarState;
import org.example.state.impl.CompletedState;
import org.example.state.impl.RepairingState;
import org.example.state.impl.WaitingState;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Car implements Callable<Void> {

    private static final Logger logger = LogManager.getLogger(Car.class);

    private final String id;
    private final int requiredParts;
    private final int repairTime;
    private final AutoService autoService;

    private CarState state;

    public Car(String id, int requiredParts, int repairTime) {
        this.id = id;
        this.requiredParts = requiredParts;
        this.repairTime = repairTime;
        this.autoService = AutoService.getInstance();
        this.state = new WaitingState();
    }

    @Override
    public Void call() throws Exception {
        logger.info("{} arrived at the auto service", id);

        state.handle(id);

        autoService.acquireResources(id, requiredParts);

        try {
            state = new RepairingState();
            state.handle(id);

            TimeUnit.SECONDS.sleep(repairTime);

            state = new CompletedState();
            state.handle(id);
        } finally {
            autoService.releaseBox(id);
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car car = (Car) o;
        return requiredParts == car.requiredParts
                && repairTime == car.repairTime
                && Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requiredParts, repairTime);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", requiredParts=" + requiredParts +
                ", repairTime=" + repairTime +
                '}';
    }
}