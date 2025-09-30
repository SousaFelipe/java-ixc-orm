package br.dev.fscarmo.ixcorm.api.records;


public class Header {

    private final String name;
    private String value;

    public static Header of(String name, String value) {
        return new Header(name, value);
    }

    public String getName() {
        return name;
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private Header(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
