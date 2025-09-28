package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;


class Cliente extends IxcOrm {

    private String razao;
    private String cnpj_cpf;

    private Cliente() {
        super("cliente");
    }

    public static Cliente newCliente() {
        return new Cliente();
    }
}


public class Main {

    static void main() {
        IxcContext.INSTANCE.setEnv(new PropertiesEnv());

        IxcResponse response = Cliente.newCliente()
                .where("razao").like("FELIPE DE SOUSA DO")
                .where("data_nascimento").greaterThanEquals("1996-11-10")
                .GET(Cliente.class);

        IO.println(response.getBody());
    }
}
