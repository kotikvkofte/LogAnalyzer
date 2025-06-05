import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.parser.LogParser;

import java.time.format.DateTimeFormatter;

public class LogParserTest {
    private final static LogParser parser = new LogParser();

    @Test
    void shouldParseBalanceInquiry() {
        String line = "[2025-05-10 09:00:22] user001 balance inquiry 1000.00";

        LogEntry entry = parser.parse(line);

        Assertions.assertEquals(OperationType.BALANCE_INQUIRY, entry.getOperationType());
        Assertions.assertEquals("user001", entry.getUser());
        Assertions.assertEquals("2025-05-10 09:00:22", entry.getDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toString()
        );
        Assertions.assertEquals(1000.00, entry.getAmount());
    }

    @Test
    void shouldParseTransfer() {
        String line = "[2025-05-10 10:25:43] user003 transferred 120.00 to user002";

        LogEntry entry = parser.parse(line);

        Assertions.assertEquals(OperationType.TRANSFERRED, entry.getOperationType());
        Assertions.assertEquals("user003", entry.getUser());
        Assertions.assertEquals("2025-05-10 10:25:43", entry.getDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toString()
        );
        Assertions.assertEquals(120.00, entry.getAmount());
        Assertions.assertEquals("user002", entry.getToUser());
    }

    @Test
    void shouldParseWithdraw() {
        String line = "[2025-05-10 23:55:32] user002 withdrew 50";

        LogEntry entry = parser.parse(line);

        Assertions.assertEquals(OperationType.WITHDREW, entry.getOperationType());
        Assertions.assertEquals("user002", entry.getUser());
        Assertions.assertEquals("2025-05-10 23:55:32", entry.getDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toString()
        );
        Assertions.assertEquals(50, entry.getAmount());
    }
}
