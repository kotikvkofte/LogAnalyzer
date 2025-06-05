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
    private LocalDateTime dateTime;
    @NonNull
    private String user;
    @NonNull
    private OperationType operationType;
    @NonNull
    private double amount;
    private String toUser;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder("[").append(dateTime.format(formatter)).append("] ")
                .append(user).append(" ")
                .append(operationType.getValue())
                .append(" ").append(amount);

        switch (operationType)
        {
            case TRANSFERRED:
                sb.append(" to ").append(toUser);
                break;
            case RECIVED:
                sb.append(" from ").append(toUser);
                break;
        }

        return sb.toString();
    }
}
