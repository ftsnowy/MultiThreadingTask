package org.example.parser;

import org.example.entity.Car;
import org.example.exception.CustomException;

import java.util.List;

public interface CarParser {

    List<Car> parseCars(List<String> lines) throws CustomException;
}