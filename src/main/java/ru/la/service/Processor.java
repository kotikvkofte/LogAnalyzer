package ru.la.service;

import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.parser.LogParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private final LogParser logParser = new LogParser();

    public Map<String, List<LogEntry>> process(List<String> lines) {
        Map<String, List<LogEntry>> map = new HashMap<>();

        for (String line : lines) {
            LogEntry logEntry = logParser.parse(line);
            if (logEntry != null) {
                if (!map.containsKey(logEntry.getUser())){
                    map.put(logEntry.getUser(), new ArrayList<>());
                }
                if(logEntry.getOperationType() == OperationType.TRANSFERRED){
                    if (!map.containsKey(logEntry.getToUser())){
                        map.put(logEntry.getToUser(), new ArrayList<>());
                    }
                    map.get(logEntry.getToUser()).add(createRecivedEntry(logEntry));
                }
                map.get(logEntry.getUser()).add(logEntry);
            }
        }

        return map;
    }

    private LogEntry createRecivedEntry(LogEntry logEntry) {
        return LogEntry.builder()
                .dateTime(logEntry.getDateTime())
                .user(logEntry.getToUser())
                .operationType(OperationType.RECIVED)
                .toUser(logEntry.getUser())
                .amount(logEntry.getAmount())
                .build();
    }
}
