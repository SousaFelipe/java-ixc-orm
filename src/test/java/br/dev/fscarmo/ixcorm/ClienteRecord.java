package br.dev.fscarmo.ixcorm;


import com.google.gson.JsonElement;


public class ClienteRecord extends IxcRecord {

    private String tipo_pessoa;
    private String cnpj_cpf;
    private String tipo_localidade;
    private String ativo;
    private String razao;
    private String endereco;
    private String numero;
    private String bairro;
    private int uf;
    private int cidade;

    /**
     * @param jsonElement Um {@link JsonElement} representando um <b>registro</b> dentro da lista de <b>registros</b>
     *                    no corpo da resposta HTTP da API do IXC Provedor.
     */
    public ClienteRecord(JsonElement jsonElement) {
        super(jsonElement);
    }

    public String getTipoPessoa() {
        return tipo_pessoa;
    }

    public void setTipoPessoa(String tipo_pessoa) {
        this.tipo_pessoa = tipo_pessoa;
    }

    public String getDocumento() {
        return cnpj_cpf;
    }

    public void setDocumento(String cnpj_cpf) {
        this.cnpj_cpf = cnpj_cpf;
    }

    public String getTipoLocalidade() {
        return tipo_localidade;
    }

    public void setTipoLocalidade(String tipo_localidade) {
        this.tipo_localidade = tipo_localidade;
    }


    public boolean isAtivo() {
        return ativo.equals("S");
    }

    public void setIsAtivo(boolean isAtivo) {
        ativo = isAtivo ? "S" : "N";
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getUf() {
        return uf;
    }

    public void setUf(int uf) {
        this.uf = uf;
    }

    public int getCidade() {
        return cidade;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }
}
