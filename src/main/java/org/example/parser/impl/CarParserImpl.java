package org.example.parser.impl;

import org.example.entity.AutoService;
import org.example.entity.Car;
import org.example.parser.CarParser;
import org.example.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

public class CarParserImpl implements CarParser {

    @Override
    public List<Car> parseCars(List<String> lines) throws CustomException {

        String[] serviceData = lines.getFirst().strip().split("\\s+");

        if (serviceData.length != 2) {
            throw new CustomException("Invalid service configuration");
        }

        int boxes = Integer.parseInt(serviceData[0]);
        int parts = Integer.parseInt(serviceData[1]);

        AutoService.getInstance().initialize(boxes, parts);

        List<Car> cars = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) {
                continue;
            }

            String[] carData = lines.get(i).strip().split("\\s+");

            if (carData.length != 3) {
                throw new CustomException(
                        "Invalid car configuration: " + lines.get(i)
                );
            }

            String id = carData[0];
            int requiredParts = Integer.parseInt(carData[1]);
            int repairTime = Integer.parseInt(carData[2]);

            cars.add(new Car(id, requiredParts, repairTime));
        }

        return cars;
    }

}