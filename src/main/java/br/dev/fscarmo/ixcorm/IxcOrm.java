package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.api.OrmClient;
import br.dev.fscarmo.ixcorm.api.Parameter;
import br.dev.fscarmo.ixcorm.api.records.Ordering;
import br.dev.fscarmo.ixcorm.api.records.Pagination;
import br.dev.fscarmo.ixcorm.enums.Operator;
import br.dev.fscarmo.ixcorm.enums.Sort;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * A classe 'IxcOrm' expõe métodos que geram uma query de busca. Além de herdar o comportamento da classe
 * {@link OrmClient} para expor os métodos que executam requisições HTTP, para a API do IXC Provedor.
 * </p>
 *
 * <p>
 * Essa classe manipula as classes de ordenação, paginação e de construção de parâmetros, e gera um JSON compatível com
 * a query de busca da API do IXC Provedor, que possui o seguinte formato:
 * </p>
 *
 * {@snippet lang=json:
 * {
 *     "qtype": "cliente",
 *     "query": "",
 *     "oper": "",
 *     "page": "1",
 *     "rg": 20,
 *     "sortname": "asc",
 *     "sortorder": "cliente.id",
 *     "grid_param": [
 *         {
 *             "TB": "cliente.razao",
 *             "OP": "L",
 *             "P": "nome do cliente (nesse caso)"
 *         }
     ]
 * }
 * }
 *
 * <p>
 * Essa classe não pode ser instanciada, pois ela existe apenas com a finalizada de encapsular toda a lógica da
 * requisição e a resposta HTTP. A maneira correta de utilizá-la é através de herança, como no exemplo a seguir:
 * </p>
 *
 * {@snippet lang = java:
 * class Cliente extends IxcOrm {
 *
 *     private Cliente() {
 *         super("cliente");
 *     }
 *
 *     public void listaClientesPorNome(String nome) {
 *         IxcResponse response = Cliente.newBuilder()
 *                .where("razao").like(nome)
 *                .GET();
 *         IO.println(response.getStatusCode());
 *         IO.println(response.getBody());
 *     }
 * }
 *}
 *
 * @author Felipe S. Carmo
 * @version 2.0.0
 * @since 2025-09-27
 */
public abstract class IxcOrm extends OrmClient {


    private final List<Parameter> parameters;
    private Ordering ordering;
    private Pagination pagination;
    private Parameter.Builder parameterBuilder;


    protected IxcOrm(String table) {
        super(table);
        parameters = new ArrayList<>();
        ordering = Ordering.ascBy(table, "id");
        pagination = Pagination.defaults();
        parameterBuilder = Parameter.newBuilder(table);
    }


    public IxcOrm setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public IxcOrm where(String column) {
        parameterBuilder.type(column);
        return this;
    }

    public IxcOrm like(Object value) {
        parameterBuilder.operator(Operator.LIKE);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm exactly(Object value) {
        parameterBuilder.operator(Operator.EQUALS);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm lessThan(Object value) {
        parameterBuilder.operator(Operator.LESS_THAN);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm lessThanEquals(Object value) {
        parameterBuilder.operator(Operator.LESS_THAN_EQUALS);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm greaterThan(Object value) {
        parameterBuilder.operator(Operator.GREATER_THAN);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm greaterThanEquals(Object value) {
        parameterBuilder.operator(Operator.GREATER_THAN_EQUALS);
        parameterBuilder.value(value);
        addParamToGridAndReset();
        setBody(getQueryAsJson());
        return this;
    }

    public IxcOrm orderBy(Sort order, String column) {
        ordering = new Ordering(column, order);
        setBody(getQueryAsJson());
        return this;
    }


    protected String getQueryAsJson() {
        String jsonGueryProps = getQueryPropsAsJson();
        String jsonGridParams = getGridParamsAsJson();
        return "{"+ jsonGueryProps + jsonGridParams +"}";
    }


    private void addParamToGridAndReset() {
        String table = getTable();
        parameters.add(parameterBuilder.build());
        parameterBuilder = Parameter.newBuilder(table);
    }

    private String getQueryPropsAsJson() {
        return "\"qtype\":\""+ getTable() +"\"," +
               "\"query\":\"\"," +
               "\"oper\":\"\"," +
               "\"page\":"+ pagination.page() +"," +
               "\"rp\":"+ pagination.rows() +"," +
               "\"sortname\":\""+ ordering.sortName() +"\"," +
               "\"sortorder\":\""+ ordering.sortOrder().value() +"\",";
    }

    private String getGridParamsAsJson() {
        StringBuilder builded = new StringBuilder().append("\"grid_param\":\"[");
        for (Parameter param : parameters) {
            builded.append(param.toString()).append(",");
        }
        return builded.append("]").toString().replace(",]", "]\"");
    }
}
