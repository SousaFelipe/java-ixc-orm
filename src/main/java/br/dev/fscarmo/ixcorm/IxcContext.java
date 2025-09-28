package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.config.Environment;


/**
 <p>
 * O enum 'IxcContext' utiliza o padrão Singleton para armazenar em memória, as configurações do ambiante de execução
 * da aplicação.
 </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-27
 */
public enum IxcContext {
    INSTANCE;

    private Environment env = null;

    IxcContext() {}

    /**
     * <p>
     * Define um novo {@link Environment} no contexto, caso já não tenha sido previamente definido.
     * </p>
     *
     * @param env Um objeto que herde da classe {@link Environment}
     */
    public void setEnv(Environment env) {
        if (this.env == null) {
            this.env = env;
        }
    }

    /**
     * @return Um {@link Environment} que tenha sido previamente definido por <b>setEnv(Environment env).</b>
     */
    public Environment getEnv() {
        return env;
    }
}
