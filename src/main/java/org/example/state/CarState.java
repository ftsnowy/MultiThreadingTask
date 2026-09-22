package org.example.state;

public interface CarState {

    void handle(String carId);

    CarState next();
}