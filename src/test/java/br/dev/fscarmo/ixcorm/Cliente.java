package br.dev.fscarmo.ixcorm;


import com.google.gson.JsonElement;


public class Cliente extends IxcOrm {


    public Cliente() {
        super("cliente");
    }

    public static Cliente newCliente() {
        return new Cliente();
    }


    public static class Record extends IxcRecord {

        private String razao;
        private String cnpj_cpf;

        public Record(JsonElement element) {
            super(element);
        }

        @Override
        protected void map() {
            mapCnpjCpf();
            mapRazao();
        }

        public String getCnpjCpf() {
            return cnpj_cpf;
        }

        public String getRazao() {
            return razao;
        }

        private void mapCnpjCpf() {
            if (elementHasProperty("cnpj_cpf")) {
                cnpj_cpf = get("cnpj_cpf").getAsString();
            }
        }

        private void mapRazao() {
            if (elementHasProperty("razao")) {
                razao = get("razao").getAsString();
            }
        }
    }
}
