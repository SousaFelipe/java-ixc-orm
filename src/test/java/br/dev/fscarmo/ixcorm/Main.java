package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;

import java.util.List;


public class Main {

    static void main() {
        IxcContext.INSTANCE.setEnv(new PropertiesEnv());

        IxcResponse response = Cliente.newCliente()
                .where("razao").like("FELIPE DE SOUSA DO")
                .where("data_nascimento").greaterThanEquals("1996-11-10")
                .GET();

        List<ClienteMapper> clientes = response.getBody().getRegistros(ClienteMapper.class);
        clientes.forEach(c -> IO.println(c.getRazao()));
    }
}