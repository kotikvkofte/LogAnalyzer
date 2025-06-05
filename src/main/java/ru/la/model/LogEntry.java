package ru.la.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

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
        StringBuilder sb = new StringBuilder("[").append(dateTime).append("] ")
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
