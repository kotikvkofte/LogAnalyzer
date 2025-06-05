package ru.la.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class LogEntry {
    @NonNull
    private final LocalDateTime dateTime;
    @NonNull
    private final String user;
    @NonNull
    private final OperationType operationType;
    @NonNull
    private final double amount;
    private final String toUser;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder("[").append(dateTime.format(formatter)).append("] ")
                .append(user).append(" ")
                .append(operationType.getValue())
                .append(" ").append(amount);

        if (toUser != null) {
            if (operationType == OperationType.TRANSFERRED) {
                sb.append(" to ").append(toUser);
            }
        }

        return sb.toString();
    }
}
