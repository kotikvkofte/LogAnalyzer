package ru.la.service.balance;

import ru.la.model.LogEntry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserBalanceCalculator implements BalanceCalculator {
    public double calculateBalance(List<LogEntry> logEntries) {
        double balance = 0;

        for (LogEntry logEntry : logEntries) {
            switch (logEntry.getOperationType()){
                case TRANSFERRED, WITHDREW -> balance -= logEntry.getAmount();
                case RECIVED -> balance += logEntry.getAmount();
                case BALANCE_INQUIRY -> balance = logEntry.getAmount();
            }
        }
        return balance;
    }
}
