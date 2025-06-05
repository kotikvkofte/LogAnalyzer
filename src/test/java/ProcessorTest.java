import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.la.model.LogEntry;
import ru.la.model.OperationType;
import ru.la.service.Processor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorTest {
    private final Processor processor = new Processor();

    @Test
    public void validMapTest() {
        List<String> inputLines = List.of(
                "[2025-05-10 10:03:23] user002 transferred 990.00 to user001",
                "[2025-05-10 10:15:56] user002 balance inquiry 110.00",
                "[2025-05-10 23:55:32] user002 withdrew 50"
        );

        Map<String, List<LogEntry>> expectedMap = new HashMap<>();
        expectedMap.put("user001", new ArrayList<>());
        expectedMap.put("user002", new ArrayList<>());

        expectedMap.get("user002").add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,3,23))
                .user("user002")
                .operationType(OperationType.TRANSFERRED)
                .amount(990.00)
                .toUser("user001")
                .build());

        expectedMap.get("user002").add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,15,56))
                .user("user002")
                .operationType(OperationType.BALANCE_INQUIRY)
                .amount(110.00)
                .build());

        expectedMap.get("user002").add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,23,55,32))
                .user("user002")
                .operationType(OperationType.WITHDREW)
                .amount(50)
                .build());

        expectedMap.get("user001").add(LogEntry.builder()
                .dateTime(LocalDateTime.of(2025,5,10,10,3,23))
                .user("user001")
                .operationType(OperationType.RECIVED)
                .amount(990.00)
                .toUser("user002")
                .build());

        Map<String, List<LogEntry>> map = processor.process(inputLines);
        Assertions.assertEquals(expectedMap, map);

    }
}
