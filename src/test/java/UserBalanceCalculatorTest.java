import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.service.balance.BalanceCalculator;
import ru.la.service.balance.UserBalanceCalculator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserBalanceCalculatorTest {
    private final BalanceCalculator calculator = new UserBalanceCalculator();

    @Test
    public void inquiryFirstTest() {
        /*
                "[2025-05-10 09:00:22] user001 balance inquiry 1000.00",
                "[2025-05-10 09:05:44] user001 transferred 100.00 to user002",
                "[2025-05-10 09:06:00] user001 transferred 120.00 to user002",
                "[2025-05-10 11:09:01] user001 transferred 235.54 to user004"
        */

        List<LogEntry> logEntries = new ArrayList<>(4);

        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,9,0,22))
                .user("user001")
                .operationType(OperationType.BALANCE_INQUIRY)
                .amount(1000)
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,9,5,44))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(100)
                .toUser("user002")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,9,6,0))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(120)
                .toUser("user002")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,11,9,1))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(235.54)
                .toUser("user004")
                .build());

        double expectedBalance = 544.46;

        var actualBalance = calculator.calculateBalance(logEntries);

        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void inquiryMiddleTest() {
        /*
            [2025-05-10 10:03:23] user001 transferred 990.00 to user002
            [2025-05-10 10:08:23] user001 transferred 311.00 to user003
            [2025-05-10 10:15:56] user001 balance inquiry 735.00
            [2025-05-10 10:28:54] user001 transferred 106.00 to user004
            [2025-05-10 23:55:32] user001 withdrew 50
        */

        List<LogEntry> logEntries = new ArrayList<>(4);


        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,3,23))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(990)
                .toUser("user002")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,8,23))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(311)
                .toUser("user003")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,9,0,22))
                .user("user001")
                .operationType(OperationType.BALANCE_INQUIRY)
                .amount(735)
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,28,54))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(106)
                .toUser("user004")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,55,32))
                .user("user001")
                .operationType(OperationType.WITHDREW)
                .amount(50)
                .build());

        double expectedBalance = 579;

        var actualBalance = calculator.calculateBalance(logEntries);

        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void inquiryLastTest() {
        /*
            [2025-05-10 10:03:23] user001 transferred 990.00 to user002
            [2025-05-10 10:08:23] user001 transferred 311.00 to user003
            [2025-05-10 10:28:54] user001 transferred 106.00 to user004
            [2025-05-10 23:55:32] user001 withdrew 50
            [2025-05-10 10:15:56] user001 balance inquiry 735.00
        */

        List<LogEntry> logEntries = new ArrayList<>(4);

        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,3,23))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(990)
                .toUser("user002")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,8,23))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(311)
                .toUser("user003")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,28,54))
                .user("user001")
                .operationType(OperationType.TRANSFERRED)
                .amount(106)
                .toUser("user004")
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,55,32))
                .user("user001")
                .operationType(OperationType.WITHDREW)
                .amount(50)
                .build());
        logEntries.add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,15,9,0,22))
                .user("user001")
                .operationType(OperationType.BALANCE_INQUIRY)
                .amount(735)
                .build());

        double expectedBalance = 735;

        var actualBalance = calculator.calculateBalance(logEntries);

        Assertions.assertEquals(expectedBalance, actualBalance);
    }
}
