package br.dev.fscarmo.ixcorm.config;


import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A classe 'EnvironmentInterpolator' encontra possíveis interpolações no arquivo de propriedades da aplicação e busca
 * seus respectivos valores nas variáveis de ambiente, atribuindo cada valor encontrado às suas respectivas
 * propriedades.
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-11-28
 */
public class EnvironmentInterpolator {

    private final Properties properties;
    private Matcher matcher;

    /**
     * @param properties A instância de {@link Properties} previamento carregardo.
     */
    public EnvironmentInterpolator(Properties properties) {
        this.properties = properties;
    }

    /**
     * Identifica template strings nas propriedades e realiza a interpolação dos seus respectivos valores com cada uma
     * das variáveis de ambiente correspondentes.
     * {@snippet lang = Properties:
     * ixc.access.token=${IXC_ACCESS_TOKEN}
     * ixc.server.domain=${IXC_SERVER_DOMAIN}
     * }
     */
    public void interpolateTemplateStringProperties() {
        var pattern = Pattern.compile("\\$\\{([^}]+)}");

        String dirtyValue;
        for (String key: properties.stringPropertyNames()) {
            dirtyValue = properties.getProperty(key);
            matcher = pattern.matcher(dirtyValue);

            if (matcher.find()) {
                interpolateEnvVar(dirtyValue, key);
            }
        }

        matcher.reset();
    }

    private void interpolateEnvVar(String dirtyValue, String key) {
        String envVarName = matcher.group(1);
        String envValue = System.getenv(envVarName);

        if (envValue != null) {
            String resolvedValue = dirtyValue.replace(matcher.group(0), envValue);
            properties.setProperty(key, resolvedValue);
        }
    }
}
