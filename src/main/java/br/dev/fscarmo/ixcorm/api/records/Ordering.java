package br.dev.fscarmo.ixcorm.api.records;


import br.dev.fscarmo.ixcorm.api.Utils;
import br.dev.fscarmo.ixcorm.enums.Sort;


public record Ordering(String sortName, Sort sortOrder) {

    public static Ordering ascBy(String table, String column) {
        String normalizedTable = Utils.Text.normalize(table);
        String normalizedColumn = Utils.Text.normalize(column);
        return new Ordering(normalizedTable + "." + normalizedColumn, Sort.ASC);
    }

    public static Ordering descBy(String table, String column) {
        String normalizedTable = Utils.Text.normalize(table);
        String normalizedColumn = Utils.Text.normalize(column);
        return new Ordering(normalizedTable + "." + normalizedColumn, Sort.DESC);
    }
}
