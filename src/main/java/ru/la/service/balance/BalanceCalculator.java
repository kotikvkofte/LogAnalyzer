package ru.la.service.balance;

import ru.la.model.LogEntry;

import java.util.List;

public interface BalanceCalculator {
    double calculateBalance(List<LogEntry> logEntries);
}
