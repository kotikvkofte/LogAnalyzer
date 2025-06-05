package ru.la.model;

public enum OperationType {
    BALANCE_INQUIRY("balance inquiry"), //запрос текущего баланса средств пользователя
    TRANSFERRED("transferred"), //перевод средств другому пользователю
    WITHDREW("withdrew"); //снятие средств

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
