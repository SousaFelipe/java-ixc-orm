package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.enums.Operator;


/**
 * <p>
 * A classe 'Parameter' representa um objeto da opção da propriedade <b>grid_param</b>, no corpo da query de busca da
 * API do IXC Provedor.
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
 * @version 1.0.1
 * @since 2025-09-27
 */
public class Parameter {

    private final String table;
    private final String operator;
    private final String value;

    private Parameter(Builder builder) {
        this.table = builder.type;
        this.operator = builder.operator.value();
        this.value = (builder.value == null) ? "" : String.valueOf(builder.value);
    }

    /**
     * <p>
     * Cria uma nova instância de {@link Builder}
     * </p>
     *
     * @param table O nome que representa a tabela do IXC, que o {@link Builder} usará para basear a construção da
     *              query de busca.
     * @return Uma nova instância de {@link Builder}.
     */
    public static Builder newBuilder(String table) {
        return new Builder(table);
    }

    /**
     * @return Uma <b>String</b> no formado JSON representando um objeto do filtro <b>grid_param.</b>
     */
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
     * A classe 'Builder' construirá um objeto da propriedade <b>grid_param</b>, no corpo da API do IXC Provedor.
     * </p>
     *
     * <p>
     * Essa classe é responsável por validar e atribuir os valores corretos a cada propriedade do objeto que fará parte
     * da lista de filtros <b>grid_param</b>.
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
            operator = Operator.EQUALS;
            type = this.table;
            value = null;
        }

        /**
         * <p>
         * Define o campo <b>TB</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param type EXEMPLO: Se definido como "id", <b>TB</b> será "tabela.id".
         */
        public void type(String type) {
            String normalizedType = Utils.Text.normalize(type);
            this.type = table +"."+ normalizedType;
        }

        /**
         * <p>
         * Define o campo <b>OP</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param operator Todos os operadores disponíveis estão em: {@link Operator}.
         */
        public void operator(Operator operator) {
            this.operator = (operator != null) ? operator : Operator.EQUALS;
        }

        /**
         * <p>
         * Define o campo <b>P</b> do objeto de busca no filtro <b>grid_param</b> do IXC Provedor.
         * </p>
         *
         * @param value Independente do tipo de valor passado, será convertido para {@link String}, por questão de
         *              compatibilidade com a  API do IXC Provedor.
         */
        public void value(Object value) {
            if (value != null) {
                this.value = value;
            }
        }

        /**
         * @return Uma nova instância de {@link Parameter}.
         */
        public Parameter build() {
            return new Parameter(this);
        }
    }
}
