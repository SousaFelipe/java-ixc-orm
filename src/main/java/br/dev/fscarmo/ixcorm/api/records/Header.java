package br.dev.fscarmo.ixcorm.api.records;


public record Header(String name, String value) {

    public static Header of(String name, String value) {
        return new Header(name, value);
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
