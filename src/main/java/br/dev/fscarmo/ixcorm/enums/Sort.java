package br.dev.fscarmo.ixcorm.enums;


/**
 * <p>
 * O enum 'Sort' serve como um wrapper para os tipos de ordenação disponíveis para listar registros do servidor do IXC
 * Provedor.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.1
 * @since 2025-09-27
 */
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
