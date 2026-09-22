package org.example.reader.impl;

import org.example.reader.TextReader;
import org.example.exception.CustomException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextReaderImpl implements TextReader {

    @Override
    public List<String> readLines(String fileName) throws CustomException {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new CustomException("Failed to read file: " + fileName);
        }
    }
}