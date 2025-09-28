package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.enums.Operator;


/**
 * <p>
 * A classe 'Parameter' representa um objeto da opção de 'grid_param', da API do IXC Provedor.
 * </p>
 *
 * <p>
 * Esta classe manipula as informações sobre a tabela a ser consultada, o operador de consulta e o valor a ser buscado,
 * com base no objeto de busca da API do IXC Provedor, que possui o seguinte formato:
 * </p>
 *
 * {@snippet lang=json:
 * {
 *     "TB": "cliente.id",
 *     "OP": "=",
 *     "P": "1234"
 * }
 * }
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-27
 */
public class Parameter {

    private final String table;
    private final String operator;
    private final String value;

    public static Builder newBuilder(String table) {
        return new Builder(table);
    }

    private Parameter(Builder builder) {
        this.table = builder.type;
        this.operator = builder.operator.value();
        this.value = (builder.value == null) ? "" : String.valueOf(builder.value);
    }

    @Override
    public String toString() {
        return "{" +
                    "\\\"TB\\\":\\\""+ this.table +"\\\"," +
                    "\\\"OP\\\":\\\""+ this.operator +"\\\"," +
                    "\\\"P\\\":\\\""+ this.value +"\\\"" +
               "}";
    }

    /**
     * <p>
     * A classe 'Builder' construirá um objeto da opção de 'grid_param', da API do IXC Provedor.
     * </p>
     *
     * <p>
     * Esta classe é responsável por validar e atribuir os valores corretos a cada propriedade do objeto que fará parte
     * da lista de consulta 'grid_param'.
     * </p>
     *
     * @author Felipe S. Carmo
     * @version 1.0.0
     * @since 2025-09-27
     */
    public static class Builder {

        private final String table;
        private Operator operator;
        private String type;
        private Object value;

        private Builder(String table) {
            this.table = Utils.Text.normalize(table);
            this.operator = Operator.EQUALS;
            this.type = this.table;
            this.value = null;
        }

        /**
         * <p>
         * Define o campo <b>TB</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param type Se definido como "id", <b>TB</b> será "tabela.id"
         */
        public void type(String type) {
            String normalizedType = Utils.Text.normalize(type);
            this.type = this.table +"."+ normalizedType;
        }

        /**
         * <p>
         * Define o campo <b>OP</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param operator Todos os operadores disponíveis estão em: {@link Operator}
         */
        public void operator(Operator operator) {
            this.operator = (operator != null) ? operator : Operator.EQUALS;
        }

        /**
         * <p>
         * Define o campo <b>P</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param value Independente do tipo de valor passado, será convertido para {@link String}.
         */
        public void value(Object value) {
            if (value != null) {
                this.value = value;
            }
        }

        /**
         * @return Uma nova instância de {@link Parameter}
         */
        public Parameter build() {
            return new Parameter(this);
        }
    }
}
