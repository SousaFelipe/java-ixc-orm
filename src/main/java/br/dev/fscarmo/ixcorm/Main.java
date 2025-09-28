package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;


class Cliente extends IxcRequest {

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

        Cliente cliente = (Cliente) Cliente.newCliente()
                .where("razao").like("FELIPE DE SOUSA DO")
                .where("data_nascimento").greaterThanEquals("1996-10-11");

        IxcResponse response = cliente.GET();
        response.print();
    }
}
