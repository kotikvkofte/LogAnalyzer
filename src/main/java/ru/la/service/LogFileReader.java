package ru.la.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogFileReader {
    public List<String> readAllLines(Path directory) throws IOException {
        List<String> lines = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.log")) {
            for (Path path : stream) {
                List<String> logLines = Files.readAllLines(path);
                lines.addAll(logLines);
            }
        }

        return lines;
    }
}
