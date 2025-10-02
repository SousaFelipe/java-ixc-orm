package br.dev.fscarmo.ixcorm.enums;


/**
 * <p>
 * O enum 'Method' serve como um wrapper para os métodos HTTP que poderão ser urilizados para realizar buscas na API
 * do IXC Provedor.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.1
 * @since 2025-09-28
 */
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
