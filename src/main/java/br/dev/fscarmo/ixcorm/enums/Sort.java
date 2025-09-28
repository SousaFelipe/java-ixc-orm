package br.dev.fscarmo.ixcorm.enums;


public enum Sort {

    ASC("asc"),
    DESC("desc");

    private final String value;

    Sort(String sort) {
        value = sort;
    }

    public String value() {
        return value;
    }
}
