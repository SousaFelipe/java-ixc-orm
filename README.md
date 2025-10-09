# IXC-ORM <small><sup>java</sup></small>
Essa biblioteca não faz parte das bibliotecas oficiais da [IXCsoft](https://ixcsoft.com/) e foi desenvolvida de forma independente e sem fins lucrativos.


### Objetivo
Esse ORM visa facilitar o consumo de dados da API oficial do [IXC Provedor](https://ixcsoft.com/ixc-provedor). Foi criado a fim de simplificar a manipulação 
dos registros do sistema, através de sua <a href="https://wikiapiprovedor.ixcsoft.com.br/">API Oficial</a>. A ideia é de que você não precise mais se 
preocupar com a construção bruta das `queries` de busca, nem com a implementação dos algorítimos de validação das requisições da API do IXC. 
Está tudo aqui, a poucas linhas de código de "distância"! 😀


### Adicionando ao projeto
> [!IMPORTANT]\
> A biblioteca ainda não está disponível nos repositórios oficiais... Por enquanto! 😉
````xml
<dependency>
    <groupId>br.dev.fscarmo</groupId>
    <artifactId>java-ixc-orm</artifactId>
    <version>1.0.0</version>
</dependency>
````


## Configuração das variáveis de ambiente
* Você poderá optar por carregar as variáveis diretamente das propriedades do seu projeto, no arquivo `application.properties`
* Ou do ambiente Docker, declarando cada uma das variáveis no seu `docker-compose.yml`


### 1 - Propriedades

````properties
# application.properties
ixc.access.token=conteúdo-do-token-gerando-dentro-do-ixc
ixc.server.domain=www.domínio-do-seu-servidor-ixc.com.br
````


### 2 - Docker

````yaml
# docker-compose.yml
environment:
  - IXC_ACCESS_TOKEN=conteúdo-do-token-gerando-dentro-do-ixc
  - IXC_SERVER_DOMAIN=www.domínio-do-seu-servidor-ixc.com.br
````


## Como utilizar

Da forma mais simples, será necessário manipular diretamente apenas três classes que estão no pacote `br.dev.fscarmo.ixcorm.*`\
São elas as classes: <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcContext.java">IxcContext</a>,
<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.java">IxcOrm</a>
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord</a>.


### 1 - Definição do método de carregamento das variáveis de ambiente

A biblioteca já possui duas classes 
(<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/DockerEnv.java">DockerEnv</a>
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/PropertiesEnv.java">PropertiesEnv</a>)
que irão facilitar o trabalho, caso você escolha declarar as variáveis de ambiente no arquivo `application.properties`, ou no ambiente Docker, 
através do arquivo `docker-compose.yml`. O exemplo a seguir mostra como informar ao contexto da biblioteca que as variáveis deverão ser carregadas do `application.properties`:

````java
import br.dev.fscarmo.ixcorm.IxcContext;
import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;

public class Main {

    public static void main(String[] args) {
        PropertiesEnv environment = new PropertiesEnv();
        IxcContext.INSTANCE.setEnv(environment);
    }
}
````
> A declaração das variáveis no arquivo `application.properties` deverá seguir o [exemplo 1](#1---propriedades),
> assim como em ambiente Docker, você deverá seguir o [exemplo 2](#2---docker), na sessão de [Configuração das variáveis de ambiente](#configuração-das-variáveis-de-ambiente).


### 2 - Declaração das classes manipuladoras

Para enviar requisições HTTP para a API do IXC Provedor, será necessário implemenrtar classes que representarão as 
tabelas que você deseja manipular. Essas classes deverão herdar da "superclasse" <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.java">IxcOrm</a>, como no 
exemplo a seguir, que simula a intenção de manipular os registros dos clientes:

````java
package br.dev.fscarmo.ixcorm;

public class Cliente extends IxcOrm {

    public Cliente() {
        super("cliente");
    }

    public static Cliente newCliente() {
        return new Cliente();
    }
}
````


### 3 - Declaração das classes "Record"

As classes `Record` são como DTOs que irão mapear, automaticamente, as propriedades de cada registro retornado pela API do IXC Provedor. 
Para isso basta criar um `Record` sendo uma "subclasse" que herde de <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord<a/> 
e declarar as propriedades que você deseja manipular, como no exemplo a seguir:

````java
import br.dev.fscarmo.ixcorm.IxcRecord;
import com.google.gson.annotations.SerializedName;

public class ClienteRecord extends IxcRecord {

    @SerializedName("cnpj_cpf")
    private String cnpjCpf;
    private String razao;
    private String endereco;
    
    /* getter's e setter's */
}
````

> [!NOTE]\
> Você pode observar que a propriedade `cnpjCpf` está anotada com @SerializedName, da biblioteca <a href="https://github.com/google/gson" target="_blank">Gson</a>.
> Isso é necessário caso você queira "extrair" corretamente a propriedade que deseja, da resposta do IXC Provedor, sem "ferir" o padrão de conversão de nomes de variáveis do Java.

### 4 - Enviando uma requisição de listagem de clientes

Utilizando as classes de exemplo, criadas no [estágio 2](#2---declaração-das-classes-manipuladoras) e no [estágio 3](#3---declaração-das-classes-record), 
para simular uma requisição de listagem dos registros de clientes cadastrados a partir de Janeiro de 2025:

````java
import br.dev.fscarmo.ixcorm.IxcResponse;

IxcResponse response = Cliente.newCliente()
        .where("data_cadastro")
        .greaterThanEquals("2025-01-01")
        .GET();

List<ClienteRecord> clientes = response.getBody().getRegistros(ClienteRecord.class);

clientes.forEach(c -> {
    System.out.println();
    System.out.println("CNPJ/CPF: " + c.getCnpjCpf());
    System.out.println("Razão social: " + c.getRazao());
    System.out.println("Endereço: " + c.getEndereco());
});
````

# Disclaimer
O código nesse repositório foi implementado por apenas uma pessoa (<a href="https://www.linkedin.com/in/fscarmo/" target="_blank">eu 😀</a>), 
nos seus raros tempos vagos!\
Estou chamando atenção para este fato, para que você, antes de utilizar essa biblioteca em algum projeto comercial, 
esteja ciente dos possíveis bugs que podem ter sidos deixados para trás.

Att. <b>Felipe S. Carmo</b>.
