import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.parser.TransferParser;
import ru.la.parser.WithdrawParser;

import java.time.format.DateTimeFormatter;

public class WithdrawParserTest {
    private final WithdrawParser parser = new WithdrawParser();

    @Test
    void testParseGoodString() {
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

    @Test
    void testParseBadString() {
        String line = "[2025-05-10 10:15:56] user002 balance inquiry 110.00";

        LogEntry entry = parser.parse(line);

        Assertions.assertNull(entry);
    }
}
