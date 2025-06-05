import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.parser.BalanceInquiryParser;

import java.time.format.DateTimeFormatter;

public class BalanceInquiryParserTest {
    private final BalanceInquiryParser parser = new BalanceInquiryParser();

    @Test
    void testParseGoodString() {
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
    void testParseBadString() {
        String line = "[2025-05-10 10:25:43] user003 transferred 120.00 to user002";

        LogEntry entry = parser.parse(line);

        Assertions.assertNull(entry);
    }
}
