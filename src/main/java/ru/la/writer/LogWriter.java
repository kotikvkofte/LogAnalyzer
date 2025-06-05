package ru.la.writer;

import ru.la.model.LogEntry;

import java.util.List;
import java.util.Map;

public interface LogWriter {
    void writeUserLog(Map<String, List<LogEntry>> userLogs);
}
