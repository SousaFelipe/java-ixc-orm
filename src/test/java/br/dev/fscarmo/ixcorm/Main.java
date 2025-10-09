package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        IxcContext.INSTANCE.setEnv(new PropertiesEnv());

        IxcResponse response = Cliente.newCliente()
                .where("razao").like("FELIPE")
                .where("data_nascimento").greaterThanEquals("1991-01-01")
                .GET();

        IxcResponseBody responseBody = response.getBody();
        List<ClienteRecord> clientes = responseBody.getRegistros(ClienteRecord.class);

        System.out.println();
        System.out.println("Total de registros encontrados: "+ responseBody.getTotal());
        System.out.println("Página: "+ responseBody.getPage());

        clientes.forEach(c -> {
            System.out.println("ID: "+ c.getId());
            System.out.println("CPF: "+ c.getDocumento());
            System.out.println("Nome: "+ c.getRazao());
            System.out.println("Endereço: "+ c.getEndereco());
            System.out.println("Número: "+ c.getNumero());
            System.out.println("Bairro: "+ c.getBairro());
            System.out.println("Cód. Cidade: "+ c.getCidade());
            System.out.println("Cód. UF: "+ c.getUf());
        });
    }
}