package br.dev.fscarmo.ixcorm.enums;


public enum Method {

    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String value;

    Method(String method) {
        this.value = method;
    }

    public String value() {
        return value;
    }
}
