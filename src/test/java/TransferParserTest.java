import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.parser.BalanceInquiryParser;
import ru.la.parser.TransferParser;

import java.time.format.DateTimeFormatter;

public class TransferParserTest {
    private final TransferParser parser = new TransferParser();

    @Test
    void testParseGoodString() {
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
    void testParseBadString() {
        String line = "[2025-05-10 10:15:56] user002 balance inquiry 110.00";

        LogEntry entry = parser.parse(line);

        Assertions.assertNull(entry);
    }
}
