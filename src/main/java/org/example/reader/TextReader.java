package org.example.reader;

import org.example.exception.CustomException;

import java.util.List;

public interface TextReader {
    List<String> readLines(String fileName) throws CustomException;
}
