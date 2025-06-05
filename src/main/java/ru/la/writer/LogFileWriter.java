package ru.la.writer;

import ru.la.model.LogEntry;
import ru.la.service.balance.BalanceCalculator;
import ru.la.service.balance.UserBalanceCalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogFileWriter implements LogWriter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path currentDirectory;
    private final BalanceCalculator calculator;

    public LogFileWriter(Path directory) {
        this.currentDirectory = directory;
        this.calculator = new UserBalanceCalculator();
    }

    @Override
    public void writeUserLog(Map<String, List<LogEntry>> map) {
        Path outputDir = currentDirectory.resolve("transactions_by_users");

        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectory(outputDir);
            }

            for (String user : map.keySet()) {
                Path userFile = outputDir.resolve(user + ".log");
                List<LogEntry> logEntries = map.get(user);

                logEntries.sort(Comparator.comparing(LogEntry::getDateTime));
                double balance = calculator.calculateBalance(logEntries);

                List<String> writeLines = new ArrayList<>(logEntries.stream().map(LogEntry::toString).toList());
                writeLines.add(getLastLine(user, balance));

                Files.write(userFile, writeLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
            System.exit(1);
        }
    }

    private String getLastLine(String user, double balance) {
        LocalDateTime nowTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(nowTime.format(FORMATTER)).append("] ")
                .append(user)
                .append(" final balance ")
                .append(balance);

        return sb.toString();
    }
}
