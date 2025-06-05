package ru.la;

import ru.la.model.LogEntry;
import ru.la.reader.LogFileReader;
import ru.la.service.Processor;
import ru.la.reader.LogReader;
import ru.la.writer.LogFileWriter;
import ru.la.writer.LogWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Укажите путь к директории с логами.");
            System.exit(1);
        }

        Path inputDir = Paths.get(args[0]);

        if (!Files.exists(inputDir)) {
            System.err.println("Не удалось найти указанный путь: " + inputDir + ".\nПроверьте правильность введенных данных.");
            System.exit(1);
        }
        if (!Files.isDirectory(inputDir)) {
            System.err.println("Указанный путь не является директорией: " + inputDir);
            System.exit(1);
        }

        LogReader logReader = new LogFileReader(inputDir);
        Processor processor = new Processor();
        LogWriter logWriter = new LogFileWriter(inputDir);

        System.out.println("Чтение логов из директории: " + inputDir);
        List<String> allLogLines = logReader.readAllLines();

        System.out.println("Обработка логов...");
        Map<String, List<LogEntry>> userLogs = processor.process(allLogLines);

        System.out.println("Сохранение файлов...");
        logWriter.writeUserLog(userLogs);

        System.out.println("Готово! Файлы созданы в: " + inputDir.resolve("transactions_by_users"));
    }
}