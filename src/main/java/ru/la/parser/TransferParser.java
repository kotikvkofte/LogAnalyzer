package ru.la.parser;

import lombok.Builder;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TransferParser implements StringOperationParser {
    private static final Pattern PATTERN = Pattern.compile("\\[(.*?)\\] (\\w+) transferred (\\d+(\\.\\d{1,2})?) to (\\w+)");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LogEntry parse(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.matches()) {
            return LogEntry.builder()
                    .dateTime(LocalDateTime.parse(matcher.group(1), FORMATTER))
                    .user(matcher.group(2))
                    .operationType(OperationType.TRANSFERRED)
                    .amount(Double.parseDouble(matcher.group(3)))
                    .toUser(matcher.group(4))
                    .build();
        }
        return null;
    }
}
