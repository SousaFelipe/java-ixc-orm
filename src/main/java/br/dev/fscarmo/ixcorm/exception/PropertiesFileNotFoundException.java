package br.dev.fscarmo.ixcorm.exception;


public class PropertiesFileNotFoundException extends IxcException {

    public PropertiesFileNotFoundException() {
        super("O arquivo 'application.properties' não foi encontrado.");
    }
}
