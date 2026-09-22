package org.example.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.CustomException;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class AutoService {

    private static final Logger logger = LogManager.getLogger(AutoService.class);

    private static final AutoService INSTANCE = new AutoService();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition resourceAvailable = lock.newCondition();

    private int totalBoxes;
    private int availableBoxes;
    private int availableParts;

    private AutoService() {
    }

    public static AutoService getInstance() {
        return INSTANCE;
    }

    public void initialize(int boxes, int parts) throws CustomException {
        if (boxes <= 0 || parts < 0) {
            throw new CustomException("Invalid auto service configuration");
        }

        lock.lock();
        try {
            totalBoxes = boxes;
            availableBoxes = boxes;
            availableParts = parts;
        } finally {
            lock.unlock();
        }

        logger.info("Auto service initialized: {} boxes, {} spare parts", boxes, parts);
    }

    public void acquireResources(String carId, int requiredParts) throws InterruptedException, CustomException {
        lock.lock();

        try {
            while (availableBoxes == 0) {
                logger.info("{} is waiting for a free repair box", carId);
                resourceAvailable.await();
            }

            if (requiredParts > availableParts) {
                logger.warn("{} cannot be serviced: required {} spare parts, but only {} are available", carId, requiredParts, availableParts);
                throw new CustomException("Service refused for " + carId + ": not enough spare parts");
            } else {
                availableBoxes--;
                availableParts -= requiredParts;
                logger.info("{} acquired a repair box and {} spare parts. Remaining spare parts: {}", carId, requiredParts, availableParts);
            }
        } finally {
            lock.unlock();
        }
    }
    public void releaseBox(String carId) {
        lock.lock();

        try {
            availableBoxes++;
            logger.info("{} released the repair box", carId);
            resourceAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoService that = (AutoService) o;
        return totalBoxes == that.totalBoxes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBoxes);
    }

    @Override
    public String toString() {
        return "AutoService{" +
                "totalBoxes=" + totalBoxes +
                ", availableBoxes=" + availableBoxes +
                ", availableParts=" + availableParts +
                '}';
    }
}