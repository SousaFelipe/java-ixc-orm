package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;

import java.util.List;


public class Main {

    static void main() {
        IxcContext.INSTANCE.setEnv(new PropertiesEnv());

        IxcResponse response = Cliente.newCliente()
                .where("razao").like("FELIPE DE SOUSA")
                .where("data_nascimento").greaterThanEquals("1991-01-01")
                .GET();

        IxcResponseBody responseBody = response.getBody();
        List<Cliente.Record> clientes = responseBody.getRegistros(Cliente.Record.class);

        IO.println("");
        IO.println("Total de registros encontrados: "+ responseBody.getTotal());
        IO.println("Página: "+ responseBody.getPage());

        clientes.forEach(c -> {
            IO.println("ID: "+ c.getId());
            IO.println("CPF: "+ c.getCnpjCpf());
            IO.println("Nome: "+ c.getRazao());
        });
    }
}