package br.dev.fscarmo.ixcorm.exception;


public class NetworkConnectionException extends IxcException {

    public NetworkConnectionException() {
        super("Falha na conexão com o servidor IXC.");
    }
}
