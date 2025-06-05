package ru.la.reader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogFileReader implements LogReader {
    private final Path directory;
    public LogFileReader(Path directory) {
        this.directory = directory;
    }

    public List<String> readAllLines() {
        List<String> lines = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.log")) {
            for (Path path : stream) {
                List<String> logLines = Files.readAllLines(path);
                lines.addAll(logLines);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            System.exit(1);
        }

        return lines;
    }
}
