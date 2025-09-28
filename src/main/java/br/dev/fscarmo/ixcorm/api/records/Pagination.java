package br.dev.fscarmo.ixcorm.api.records;


public record Pagination(int page, int rows) {

    public static Pagination defaults() {
        return new Pagination(1, 20);
    }
}
