# IXC-ORM <small><sup>java</sup></small>

Este ORM visa facilitar o consumo de dados da API oficial do [IXC Provedor](https://ixcsoft.com/ixc-provedor).\
Esta biblioteca não faz parte das bibliotecas oficiais da [IXCsoft](https://ixcsoft.com/) e foi desenvolvida de forma independente e sem fins lucrativos.


## Adicionando ao projeto

> **ATENÇÃO:** Esta biblioteca ainda não está disponível no <a href="mvnrepository.com" target="_blank">Maven Repository</a>!

````xml
<dependency>
    <groupId>br.dev.fscarmo</groupId>
    <artifactId>java-ixc-orm</artifactId>
    <version>1.0.0</version>
</dependency>
````


## Configuração das variáveis de ambiente

> 1 - Você poderá optar por carregar as variáveis de um arquivo `application.properties`\
> 2 - Ou do ambiente Docker, declarando cada uma delas no seu `docker-compose.yml`


### 1 - Propriedades

````properties
ixc.access.token=conteúdo-do-token-gerando-dentro-do-ixc
ixc.server.domain=www.domínio-do-seu-servidor-ixc.com.br
````


### 2 - Dokcer

````yaml
environment:
  - IXC_ACCESS_TOKEN=conteúdo-do-token-gerando-dentro-do-ixc
  - IXC_SERVER_DOMAIN=www.domínio-do-seu-servidor-ixc.com.br
````


## Como utilizar

Da forma mais simples, será necessário manipular apenas três classes que estão no pacote `br.dev.fscarmo.iscorm.*`.
São elas as classes: <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcContext.java">IxcContext</a>,
<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.java">IxcOrm</a>
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord</a>.

### Definindo método de carregamento das variáveis de ambiente

A biblioteca já possui duas classes 
(<a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/DockerEnv.java">DockerEnv</a>
e <a href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/config/envs/PropertiesEnv.java">PropertiesEnv</a>)
que irão facilitar o trabalho, caso você escolha declarar as variáveis de ambiente no arquivo `application.properties`, 
ou no ambiente Docker, através do arquivo `docker-compose.yml`. O exemplo a seguir, mostra como informar ao contexto 
da biblioteca que as variáveis foram configuradas no aquivo `application.properties`:

````java
import br.dev.fscarmo.ixcorm.IxcContext;
import br.dev.fscarmo.ixcorm.config.envs.PropertiesEnv;

public class Main {

    public static void main(String[] args) {
        IxcContext.INSTANCE.setEnv(new PropertiesEnv());
    }
}
````
> A declaração das variáveis no arquivo `application.properties` deverá seguir o [exemplo 1](#1---propriedades),
> assim como em ambiente Docker, você deverá seguir o [exemplo 2](#2---dokcer), na sessão de [Configuração das variáveis de ambiente](#configuração-das-variáveis-de-ambiente).

### Declarando as classes que irão solicitar registros do IXC Provedor
