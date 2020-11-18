# data-analysis
Aplicação para análise de dados de vendas em arquivo texto padronizado.

A aplicação supõe a existência de um diretório base (basefolder) com um subdiretório "/data/in" contendo arquivos do tipo ".dat" cujas linhas contém informações de vendas e vendedores no formato esperado. 
Assim, busca-se os arquivos padronizados no diretório de entrada ("{basefolder}/data/in") e grava um relatório em um arquivo no diretório de saída ("{basefolder}/data/out").
O nome do arquivo gerado com o relatório terá o padrão {flat_file_name}.done.dat

A aplicação buscará os arquivos, por padrão, a cada segundo.
Pode-se alterar o intervalo de busca de arquivos configurando uma expressão cron no parâmetro 'data-analysis.job.interval.cron'

Formatos de entrada esperados: 
- Dados do vendedor: 001çCPFçNameçSalary
- Dados do cliente: 002çCNPJçNameçBusiness Area
- Dados de vendas: 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

Conteúdo do arquivo de saída:
- Quantidade de clientes no arquivo de entrada
- Quantidade de vendedor no arquivo de entrada
- ID da venda mais cara
- O pior vendedor

## Tecnologias Utilizadas
- Java 14
- SpringBoot 2.3.3
- Lombok
- Junit + Mockito

## Rodando localmente
Para rodar a aplicação em sua máquina siga os passos: 
   ```sh
    ./gradlew bootRun --args='--data-analysis.file.path=basefolder' 
   ```

## Rodando com o Docker
Para rodar com o Docler: 
- Buildar a aplicação
    ```sh 
     ./gradlew build
    ```
- Criar a imagem Docker
    ```sh
     docker build -t nome_imagem . 
   ```
- Rodar a imagem 
    ```sh
     docker run nome_imagem 
  ```

## Qualidade
- Para executar os testes unitários   
   ```sh
    ./gradlew test
   ```

## Lombok
Este projeto utiliza o [Lombok](https://projectlombok.org/)

Instale o plugin do lombok e map-struct no IntelliJ de acordo com as Instruções:
- Habilite o plugin em Settings > Plugins > Lombok
- Habilite a opção **annotation processors** em Settings > Build, Execution, Deployment > Compiler > Annotation Processors  
