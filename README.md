# data-analysis
Aplicação para analise de dados de vendas em arquivo texto padronizado.
Busca arquivos padronizados no diretório de entrada ("/in") e grava um relatório em um arquivo no diretório de saída ("/out").

Ao rodar essa aplicação espera-se que exista um diretório base (basefolder) com um subdiretório "/in" que contenha arquivos do tipo ".dat" que tenha linhas com informações de 
vendas e vendedores no formato esperado. A aplicação buscará arquivos nesse subdiretório a cada segundo e gerará no diretório de saída um arquivo com as 
informações resumidas.

Pode-se alterar o intervalo de busca de arquivos configurando uma expressão cron no parâmetro 'data-analysis.job.interval.cron'

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
