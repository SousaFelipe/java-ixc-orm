package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.api.IxcRecordMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * <p>
 * A classe 'IxcRecord' serve como classe padrão para o mapeamento de objetos retornados pela API do IXC Provedor.
 * Ela não realiza o mapeamento de forma automática, isso deve ser implementado por uma classe filha, dentro do método
 * map().
 * </p>
 *
 * <p>
 * Por padrão, a classe 'IxcRecord' já mapeia a propriedade "id" do registro.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-28
 */
public abstract class IxcRecord {

    private final JsonElement jsonElement;
    private Integer id;

    /**
     * @param jsonElement Um {@link JsonElement} representando um <b>registro</b> dentro da lista de <b>registros</b>
     *                    no corpo da resposta HTTP da API do IXC Provedor.
     */
    public IxcRecord(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
        IxcRecordMapper.map(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>
     * Melhor opção para utilizar, quando se pretende enviar o registro pelo corpo de uma requisição, a fim de
     * inseri-lo ou atualizá-lo.
     * </p>
     *
     * @return Uma {@link String} no formato JSON, sem a propriedade "id" do registro.
     */
    public String toJsonString() {
        JsonObject copyElement = jsonElement.getAsJsonObject();
        copyElement.remove("id");
        return copyElement.toString();
    }

    /**
     * @return Uma {@link String} com todas as propriedades do registro.
     */
    @Override
    public String toString() {
        return jsonElement.getAsJsonObject().toString();
    }

    /**
     * @param property O nome da propriedade a ser obtida no objeto {@link JsonElement}.
     * @return Um {@link JsonObject} com a proriedade passada em <b>property</b>. Se <b>property</b> não for
     * encontrada, será retornado um <b>null.</b>
     */
    public JsonElement getJsonElement(String property) {
        return jsonElement.getAsJsonObject().get(property);
    }
}
