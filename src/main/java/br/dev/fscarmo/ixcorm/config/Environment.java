package br.dev.fscarmo.ixcorm.config;


/**
 * <p>
 * A classe 'Environment' deverá ser herdada, para que você possa construir sua própria maneira de carregar as
 * variáveis de ambiente na sua aplicação.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-27
 */
public abstract class Environment {

    private String token;
    private String domain;

    /**
     * Define o valor da variável de ambiente que armazena o token de acesso gerado pelo sistema IXC Provedor.
     *
     * <p>
     * Se a variável de ambiente já tiver sido definida por <b>setToken(token)</b>, ela não será sobrescrita.
     * </p>
     *
     * @param token O novo valor do token
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
     * Define o valor da variável de ambiente que armazena o domínio do servidor do IXC Provedor.
     * </p>
     *
     * <p>
     * Se a variável de ambiente já tiver sido definida por <b>setDomain(domínio)</b>, ela não será sobrescrita.
     * </p>
     *
     * @param domain O novo valor do domínio
     */
    protected void setDomain(String domain) {
        boolean newDomainIsValid = (domain != null && !domain.isBlank());
        boolean oldDomainIsEmpty = (this.domain == null || this.domain.isBlank());
        if (newDomainIsValid && oldDomainIsEmpty) {
            this.domain = domain;
        }
    }

    /**
     * @return O <b>Token</b> previamente definido por <b>setToken(String token)</b>
     */
    public String getToken() {
        return token;
    }

    /**
     * @return O <b>Domínio</b> previamente definido por <b>setDomain(String domain)</b>
     */
    public String getDomain() {
        return domain;
    }
}
