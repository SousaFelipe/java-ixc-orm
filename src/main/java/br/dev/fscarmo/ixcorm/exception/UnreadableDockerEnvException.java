package br.dev.fscarmo.ixcorm.exception;


public class UnreadableDockerEnvException extends RuntimeException {

    public UnreadableDockerEnvException(String variable) {
        super("Certifique-se de que você configurou a variável '"+ variable +"' no seu 'docker-compose.yml'");
    }
}
