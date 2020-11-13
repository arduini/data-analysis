# data-analysis
Aplicação para analise de dados de vendas em arquivo texto padronizado.
Busca arquivos padronizados no diretório de entrada ("/in") e grava um relatório em um arquivo no diretório de saída ("/out").

## Rodando localmente
Para rodar a aplicação em sua máquina siga os passos: 

   ``` ./gradlew bootRun --args='--data-analysis.file.path=basefolder' ```

## Rodando com o Docker
Para rodar com o Docler: 
- Buildar a aplicação
    ``` ./gradlew build ```
- Criar a imagem Docker
    ``` docker build -t nome_imagem . ```
- Rodar a imagem 
    ``` docker run nome_imagem ```

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
