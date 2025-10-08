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

## Como utilizar

No modo mais básico de utilização, será necessário manipular apenas duas classes da biblioteca, ambas se encontram 
dentro do pacote `br.dev.fscarmo.iscorm.*`. São elas, as classes: <a href="https://github.
com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcContext.java">IxcContext</a>, <a
href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcOrm.
java">IxcOrm</a> e <a
href="https://github.com/SousaFelipe/java-ixc-orm/blob/master/src/main/java/br/dev/fscarmo/ixcorm/IxcRecord.java">IxcRecord</a>