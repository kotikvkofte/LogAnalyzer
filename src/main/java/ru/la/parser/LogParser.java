package ru.la.parser;

import ru.la.model.LogEntry;

import java.util.List;

public class LogParser {
    private final List<StringOperationParser> stringParsers;

    public LogParser() {
        this.stringParsers = List.of(
                new WithdrawParser(),
                new TransferParser(),
                new BalanceInquiryParser()
        );
    }

    public LogEntry parse(String line) {
        for (StringOperationParser parser : stringParsers) {
            var result = parser.parse(line);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
