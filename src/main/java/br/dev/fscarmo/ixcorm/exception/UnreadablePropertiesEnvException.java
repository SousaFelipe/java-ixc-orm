package br.dev.fscarmo.ixcorm.exception;


public class UnreadablePropertiesEnvException extends RuntimeException {

    public UnreadablePropertiesEnvException(String variable) {
        super("A variável '"+ variable +"' não foi encontrada no arquivo 'application.properties'");
    }
}
