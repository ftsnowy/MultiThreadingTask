package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Car;
import org.example.parser.CarParser;
import org.example.parser.impl.CarParserImpl;
import org.example.reader.TextReader;
import org.example.reader.impl.TextReaderImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static final String SERVICE_CONFIG =
            "src/main/resources/service.txt";

    static void main(String[] args) {
        logger.info("Starting auto service simulation");

        TextReader reader = new TextReaderImpl();
        CarParser parser = new CarParserImpl();

        try {
            List<String> dataLines = reader.readLines(SERVICE_CONFIG);
            List<Car> cars = parser.parseCars(dataLines);

            ExecutorService executor = Executors.newFixedThreadPool(cars.size());

            for (Car car : cars) {
                executor.submit(car);
            }

            executor.shutdown();

            if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.info("Auto service simulation completed");
            } else {
                logger.warn("Simulation timeout exceeded");
            }

        } catch (Exception e) {
            logger.error("Auto service simulation failed", e);
        }
    }
}