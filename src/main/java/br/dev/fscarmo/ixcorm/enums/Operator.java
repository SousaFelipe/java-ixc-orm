package br.dev.fscarmo.ixcorm.enums;


public enum Operator {

    EQUALS("="),
    LIKE("L"),
    LESS_THAN("<"),
    LESS_THAN_EQUALS("<="),
    GREATER_THAN(">"),
    GREATER_THAN_EQUALS(">=");

    private final String value;

    Operator(String operator) {
        value = operator;
    }

    public String value() {
        return value;
    }
}
