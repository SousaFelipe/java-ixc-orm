package br.dev.fscarmo.ixcorm.config.envs;


import br.dev.fscarmo.ixcorm.config.Environment;
import br.dev.fscarmo.ixcorm.exception.UnreadableDockerEnvException;


/**
 * <p>
 * A classe 'DockerEnv' serve como um wrapper para acesso das variáveis de ambiente gerenciadas pelo Docker.
 * </p>
 *
 * <p>
 * Para utilizar esse wrapper, certifique-se de inserir no seu arquivo <b>docker-compose.yml</b> as seguintes variáveis:
 * </p>
 *
 * {@snippet lang=yaml:
 * environment:
 *     - IXC_ACCESS_TOKEN=conteúdo-do-token-gerando-dentro-do-ixc
 *     - IXC_SERVER_DOMAIN=www.domínio-do-seu-servidor-ixc.com.br
 * }
 *
 * @author Felipe S. Carmo
 * @version 1.0.1
 * @since 2025-09-27
 */
public class DockerEnv extends Environment {

    private static final String IXC_ACCESS_TOKEN = "IXC_ACCESS_TOKEN";
    private static final String IXC_SERVER_DOMAIN = "IXC_SERVER_DOMAIN";

    public DockerEnv() {
        String token = getEnvVarFromSystem(IXC_ACCESS_TOKEN);
        String domain = getEnvVarFromSystem(IXC_SERVER_DOMAIN);
        setToken(token);
        setDomain(domain);
    }

    private String getEnvVarFromSystem(String key) throws UnreadableDockerEnvException {
        String envVar = System.getenv(key);
        if (envVar == null) {
            throw new UnreadableDockerEnvException(key);
        }
        return envVar;
    }
}
