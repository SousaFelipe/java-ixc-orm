package br.dev.fscarmo.ixcorm.config;


/**
 * <p>
 * A classe 'Environment' possibilita, através de herança, formas distintas de carregar as variáveis de ambiente da
 * biblioteca. No pacote {@link br.dev.fscarmo.ixcorm.config.envs} existem duas classes wrapper, para carregar as
 * variáveis através do <b>Docker</b> ou de <b>Application Properties.</b>
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.2
 * @since 2025-09-27
 */
public abstract class Environment {

    private String token;
    private String domain;

    /**
     * <p>
     * Define o valor da variável de ambiente que armazena o token de acesso gerado dentro do IXC Provedor.
     * </p>
     *
     * <p>
     * Se a variável de ambiente já tiver sido definida por <b>setToken(String token)</b>, ela não será sobrescrita.
     * </p>
     *
     * @param token O novo valor do token.
     */
    protected void setToken(String token) {
        boolean newTokenIsValid = (token != null && !token.isBlank());
        boolean oldTokenIsEmpty = (this.token == null || this.token.isBlank());
        if (newTokenIsValid && oldTokenIsEmpty) {
            this.token = token;
        }
    }

    /**
     * <p>
     * Define o valor da variável de ambiente que armazena o domínio do servidor IXC Provedor.
     * </p>
     *
     * <p>
     * Se a variável de ambiente já tiver sido definida por <b>setDomain(String domain)</b>, ela não será sobrescrita.
     * </p>
     *
     * @param domain O novo valor do domínio.
     */
    protected void setDomain(String domain) {
        boolean newDomainIsValid = (domain != null && !domain.isBlank());
        boolean oldDomainIsEmpty = (this.domain == null || this.domain.isBlank());
        if (newDomainIsValid && oldDomainIsEmpty) {
            this.domain = domain;
        }
    }

    /**
     * <p>
     * Recupera o valor do token previamente definido por <b>setToken(String token).</b>
     * </p>
     *
     * @return Uma {@link String} com o valor do <b>Token.</b>
     */
    public String getToken() {
        return token;
    }

    /**
     * <p>
     * Recupera o valor do dompinio previamente definido por <b>setDomain(String domain).</b>
     * </p>
     *
     * @return Uma {@link String} com o valor do <b>Domínio.</b>
     */
    public String getDomain() {
        return domain;
    }
}
