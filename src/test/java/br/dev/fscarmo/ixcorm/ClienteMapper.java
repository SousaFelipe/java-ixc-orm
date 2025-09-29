package br.dev.fscarmo.ixcorm;


import com.google.gson.JsonElement;


public class ClienteMapper extends IxcRecordMapper {


    private String razao;
    private String cnpj_cpf;


    public ClienteMapper(JsonElement element) {
        super(element);
    }

    @Override
    protected void map() {
        cnpj_cpf = elementHasProperty("cnpj_cpf") ? get("cnpj_cpf").getAsString() : "";
        razao = elementHasProperty("razao") ? get("razao").getAsString() : "";
    }

    public String getCnpjCpf() {
        return cnpj_cpf;
    }

    public String getRazao() {
        return razao;
    }
}
