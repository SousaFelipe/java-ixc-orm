package br.dev.fscarmo.ixcorm.config.envs;


import br.dev.fscarmo.ixcorm.config.Environment;
import br.dev.fscarmo.ixcorm.exception.PropertiesFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;


/**
 * <p>
 * A classe 'PropertiesEnv' serve como um wrapper para acesso das variáveis de ambiente, definidas nas propriedades da
 * aplicação.
 * </p>
 *
 * <p>
 * Para utilizar esse wrapper, certifique-se de inserir no seu arquivo <b>application.properties</b> as seguintes
 * propriedades:
 * </p>
 *
 * {@snippet lang=Properties:
 * ixc.access.token=conteúdo-do-token-gerando-dentro-do-ixc
 * ixc.server.domain=www.domínio-do-seu-servidor-ixc.com.br
 * }
 *
 * @author Felipe S. Carmo
 * @version 1.0.1
 * @since 2025-09-27
 */
public class PropertiesEnv extends Environment {

    public static final String IXC_ACCESS_TOKEN = "ixc.access.token";
    public static final String IXC_SERVER_DOMAIN = "ixc.server.domain";

    public PropertiesEnv() {
        String token = getEnvVarFromProperties(IXC_ACCESS_TOKEN).orElse(null);
        String domain = getEnvVarFromProperties(IXC_SERVER_DOMAIN).orElse(null);
        setToken(token);
        setDomain(domain);
    }

    private Optional<String> getEnvVarFromProperties(String key) {

        InputStream stream = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = getPropertiesFromStream(stream);
        String envVarValue = properties.getProperty(key);

        if (envVarValue != null && !envVarValue.isBlank()) {
            return Optional.of(envVarValue);
        }

        return Optional.empty();
    }

    private Properties getPropertiesFromStream(InputStream stream) throws RuntimeException {
        try {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        }
        catch (IllegalArgumentException | NullPointerException | IOException e) {
            if (e instanceof NullPointerException) {
                throw new PropertiesFileNotFoundException();
            }
            throw new RuntimeException(e);
        }
    }
}
