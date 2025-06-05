package ru.la.parser;

import ru.la.model.LogEntry;

public interface StringOperationParser {
    LogEntry parse(String value);
}
