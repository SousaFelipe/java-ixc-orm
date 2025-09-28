package br.dev.fscarmo.ixcorm.exception;


public class PropertiesFileNotFoundException extends RuntimeException {

    public PropertiesFileNotFoundException() {
        super("O arquivo 'application.properties' não foi encontrado.");
    }
}
