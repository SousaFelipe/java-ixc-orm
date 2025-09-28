package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.api.records.Ordering;
import br.dev.fscarmo.ixcorm.api.records.Pagination;
import br.dev.fscarmo.ixcorm.enums.Operator;
import br.dev.fscarmo.ixcorm.enums.Sort;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * A classe 'ORM' expõe métodos que geram uma query de busca para a API do IXC Provedor.
 * </p>
 *
 * <p>
 * Esta classe manipula as classes de ordenação, paginação e de construção de parâmetros, e gera um JSON compatível com
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
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-27
 */
public abstract class ORM {


    private final String table;
    private final List<Parameter> parameters;

    private Ordering ordering;
    private Pagination pagination;
    private Parameter.Builder parameterBuilder;


    protected ORM(String table) {
        this.table = table;
        this.parameters = new ArrayList<>();
        this.ordering = Ordering.ascBy(table, "id");
        this.pagination = Pagination.defaults();
        this.parameterBuilder = Parameter.newBuilder(table);
    }


    public ORM setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public ORM where(String column) {
        this.parameterBuilder.type(column);
        return this;
    }

    public ORM like(Object value) {
        this.parameterBuilder.operator(Operator.LIKE);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM exactly(Object value) {
        this.parameterBuilder.operator(Operator.EQUALS);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM lessThan(Object value) {
        this.parameterBuilder.operator(Operator.LESS_THAN);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM lessThanEquals(Object value) {
        this.parameterBuilder.operator(Operator.LESS_THAN_EQUALS);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM greaterThan(Object value) {
        this.parameterBuilder.operator(Operator.GREATER_THAN);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM greaterThanEquals(Object value) {
        this.parameterBuilder.operator(Operator.GREATER_THAN_EQUALS);
        this.parameterBuilder.value(value);
        this.addParamToGridAndReset();
        return this;
    }

    public ORM orderBy(Sort order, String column) {
        this.ordering = new Ordering(column, order);
        return this;
    }


    protected String getTable() {
        return this.table;
    }

    protected String getQueryAsJSON() {
        String jsonGueryProps = this.buildedQueryProps();
        String jsonGridParams = this.buildedGridParams();
        return "{"+ jsonGueryProps + jsonGridParams +"}";
    }


    private void addParamToGridAndReset() {
        this.parameters.add(this.parameterBuilder.build());
        this.parameterBuilder = Parameter.newBuilder(this.table);
    }

    private String buildedQueryProps() {
        return "\"qtype\":\""+ this.table +"\"," +
               "\"query\":\"\"," +
               "\"oper\":\"\"," +
               "\"page\":"+ this.pagination.page() +"," +
               "\"rp\":"+ this.pagination.rows() +"," +
               "\"sortname\":\""+ this.ordering.sortName() +"\"," +
               "\"sortorder\":\""+ this.ordering.sortOrder().value() +"\",";
    }

    private String buildedGridParams() {
        StringBuilder builded = new StringBuilder().append("\"grid_param\":\"[");
        for (Parameter param : this.parameters) {
            builded.append(param.toString()).append(",");
        }
        return builded.append("]").toString().replace(",]", "]\"");
    }
}
