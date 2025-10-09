<div align="center">
    
# IXC-ORM

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
[![License](https://img.shields.io/badge/License-Public%20Domain-green.svg?style=for-the-badge)](https://github.com/SousaFelipe/java-ixc-orm/blob/master/LICENSE)
![Status](https://img.shields.io/badge/Status-Stable-brightgreen?style=for-the-badge)

</div>


<div align="justify">

Esse ORM visa facilitar a manipulação de registros do sistema <a href="https://ixcsoft.com/ixc-provedor/">IXC Provedor</a>, através de sua <a href="https://wikiapiprovedor.ixcsoft.com.br/">API Oficial</a>.
Não precisa mais se preocupar com a construção das `queries` de busca, com a manipulação dos cabeçalhos, ou validação das requisições HTTP. Já está tudo aqui, a poucas linhas de código de "distância"! 😀
    
</div>


### Download

> [!NOTE]\
> Requer a versão 21 ou superior do Java

````xml
<dependency>
    <groupId>br.dev.fscarmo</groupId>
    <artifactId>ixcorm</artifactId>
    <version>1.0.0</version>
</dependency>
````


## Configuração das variáveis de ambiente
Você poderá optar por configurar as variáveis nas propriedades do seu projeto, no arquivo `application.properties`.\
Ou no ambiente Docker, declarando cada uma delas no seu `docker-compose.yaml`. (**sugestão**: combinar com arquivo .env)

> Particularmente falando, eu utilizo as duas configurações, sendo `application.properties` para testes e `docker-compose.yaml` para produção. 🙂


### 1 - Propriedades

````properties
# application.properties
ixc.access.token=conteúdo-do-token-gerando-dentro-do-ixc
ixc.server.domain=www.domínio-do-seu-servidor-ixc.com.br
````


### 2 - Docker

````env
# .env
IXC_ACCESS_TOKEN=conteúdo-do-token-gerando-dentro-do-ixc
IXC_SERVER_DOMAIN=www.domínio-do-seu-servidor-ixc.com.br
````

````yaml
# docker-compose.yaml
environment:
  - IXC_ACCESS_TOKEN=${IXC_ACCESS_TOKEN}
  - IXC_SERVER_DOMAIN=${IXC_SERVER_DOMAIN}
````


## Como utilizar

Da forma mais simples, será necessário manipular diretamente apenas quatro classes que estão no pacote `br.dev.fscarmo.ixcorm.*`\
São elas as classes: <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcContext.java">IxcContext</a>, 
<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.java">IxcOrm</a>, 
<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcResponse.java">IxcResponse</a> 
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord</a>. 
Sendo que as classes <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.java">IxcOrm</a> e 
<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcResponse.java">IxcResponse</a> só poderão 
ser manipuladas através de subclasses.


### 1 - Definição do método de carregamento das variáveis de ambiente

A biblioteca já possui duas classes 
(<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/DockerEnv.java">DockerEnv</a>
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/PropertiesEnv.java">PropertiesEnv</a>)
que irão facilitar o trabalho, caso você escolha declarar as variáveis de ambiente no arquivo `application.properties`, ou no ambiente Docker, 
através do arquivo `docker-compose.yaml`. O exemplo a seguir mostra como informar ao contexto da biblioteca que as variáveis deverão ser carregadas do `application.properties`:

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

As classes `Record` são como DTOs que irão mapear automaticamente as propriedades de cada registro retornado pela API do IXC Provedor. 
Para isso basta criar uma classe `Record` sendo ela uma "subclasse" de <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord<a/> 
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

Utilizando as classes criadas no [exemplo 2](#2---declaração-das-classes-manipuladoras) e no [exemplo 3](#3---declaração-das-classes-record), 
para simular uma requisição de listagem dos registros de clientes cadastrados a partir de Janeiro de 2025:

````java
import br.dev.fscarmo.ixcorm.IxcResponse;
import java.util.List;

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


# Contribuições

Contribuições são sempre bem-vindas!\
Se você conhece uma maneira melhor de fazer algo, por favor, me avise!
Caso contrário, é sempre melhor fazer um PR na branch master.

At.te,\
<b>Felipe S. Carmo</b>.
