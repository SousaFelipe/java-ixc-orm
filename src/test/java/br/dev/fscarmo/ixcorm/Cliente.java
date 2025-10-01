package br.dev.fscarmo.ixcorm;


public class Cliente extends IxcOrm {

    public Cliente() {
        super("cliente");
    }

    public static Cliente newCliente() {
        return new Cliente();
    }
}
