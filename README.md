# 🦎 Geckonect API - Sistema de Recomendação de Carreira e Trilhas com IA

## O Problema e a Solução

O futuro do trabalho, impulsionado pela IA e automação, expõe profissionais a uma busca urgente por requalificação. Com a projeção de que inúmeras profissões se tornarão obsoletas até 2030, o risco de desemprego massivo é iminente, e a educação tradicional se mostra incapaz de preparar o trabalhador para essa adaptação acelerada. A falta de um direcionamento estratégico e personalizado leva à sobrecarga e ao risco de burnout, agravado pela pressão corporativa e pela falta de atenção à saúde mental.

A Geckonect resolve essa dor estabelecendo-se não como uma plataforma de ensino, mas como um assistente de carreira e ecossistema de aprendizado baseado em três pilares: Requalificação, Reaproveitamento de Conhecimento e Saúde Mental.

Através de um diagnóstico inicial e de uma inteligência artificial, a Geckonect realiza uma análise completa, mapeando a área atual, o risco de extinção da profissão e, crucialmente, o nível de estresse do usuário. Com esses dados, a IA sugere um plano de estudos contínuo que alinha as demandas do mercado com a realidade da pessoa, combinando:

Trilhas de Hard e Soft Skills essenciais para as carreiras do futuro.

Trilhas de Autodidatismo e Gestão.

Trilhas de Saúde Mental e Estabilidade Emocional, garantindo que o avanço profissional seja feito de forma estratégica e sustentável.

Assim, o Geckonect oferece o direcionamento proativo e acolhedor que o trabalhador moderno precisa para navegar com sucesso nas transformações do mercado e evitar a obsolescência profissional.

## 🌟 Visão Geral do Projeto

A **Geckonect** é uma API RESTful desenvolvida em **Java 17 (Spring Boot 3)** que implementa um sistema inovador de recomendação de trilhas de aprendizado e *reskilling*. O diferencial central é a **lógica de simulação de Inteligência Artificial** que cruza dados de **carreira**, **interesses** e, crucialmente, **saúde mental** do usuário para gerar uma recomendação personalizada.

O projeto utiliza o banco de dados **H2 em memória** para desenvolvimento e testes, e segue o padrão **DDD (Domain-Driven Design)** (Controller → Service → Repository).

### Pilares da IA Simulado

1.  **Risco de Automação:** Analisa a carreira atual e o tempo de experiência para determinar a urgência do *reskilling*.
2.  **Reutilização de Conhecimento:** Mapeia os interesses e gostos do usuário para áreas do futuro (Dados, Gestão, Inovação).
3.  **Saúde Mental:** Interpreta os níveis de estresse, energia e equilíbrio para priorizar trilhas de Bem-Estar em casos críticos.

***

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Persistência:** Spring Data JPA + Hibernate
* **Banco de Dados:** H2 Database (em memória)
* **Validação:** Bean Validation (`jakarta.validation`)
* **Documentação:** SpringDoc / Swagger UI
* **Arquitetura:** Camadas Controller, Service e Repository.
* **Segurança:** Spring Security (configurado com permissão total para testes em ambiente DEV).


## ⚙️ Configuração e Execução

* **Java Version:** 21 ou 17.
* **Carga de Dados:** O projeto carrega o `schema.sql` e `data.sql` automaticamente com usuários, trilhas e questionários iniciais (IDs 1, 3).

### Instruções de Execução

1.  Navegue até o diretório raiz do projeto.
2.  Execute o comando para inicialização:

    ```bash
    mvn spring-boot:run
    ```

A API estará acessível em: `http://localhost:8080/`

A documentação interativa (Swagger UI) está em: `http://localhost:8080/swagger-ui.html`

***

## 🧪 Como Testar a API (Insomnia)

Algumas sugestões para você testar as funcionalidades da API. 

### Teste da IA

O endpoint `/recomendacao/{id}` utiliza a lógica de IA. Teste os dois cenários mais importantes para validar as regras de negócio:

### 1. Teste de Reskilling e Risco de Automação 

Este cenário testa o pilar de **Saúde Mental**.

* **Requisição:**
    ```http
    GET http://localhost:8080/recomendacao/2
    ```
    
Este cenário testa o pilar de **Risco de Automação**.

* **Requisição:**
    ```http
    GET http://localhost:8080/recomendacao/1
    ```

### 2. Criação de um novo questionário

Este cenário cria um novo questionário com as informações do usuário.

* **Requisição:**
    ```http
    POST http://localhost:8080/questionarios
    ```
* **Exemplo de body:**
  ```
      {
        "usuarioId": 4, 
        "carreiraAtual": "Analista de Logística Senior",
        "tempoCarreiraAtualAnos": 12,
        "jaTrabalhouOutraCarreira": true,
        "carreirasAnteriores": "Auxiliar Administrativo",
        "gostaDeFazer": "Analisar números e otimizar processos.",
        "gostaDeEstudar": "Big Data e novas tecnologias.",
        "modoEstudoPreferido": "PRATICA",
        "interesseMigracaoTipo": "MUDAR_DE_AREA",
        "areasInteresseFuturo": "Data Science",
        "disponibilidadeHorasSemana": 10,
        "objetivoCarreira": "Requalificação profissional imediata.",
        "estresse": 2,
        "energia": 5,
        "equilibrioVidaTrabalho": 4,
        "autoCobranca": 3
      }
  ```

### 3. Teste do Exception 404 de Usuarios

Este cenário testa o erro 404 personalizado para Usuario não encontrado.

* **Requisição:**
    ```http
    GET http://localhost:8080/usuarios/999
    ```

### 4. Consultar trilhas

Este cenário consulta as trilhas cadastradas previamente (Seeds).

* **Requisição:**
    ```http
    GET http://localhost:8080/trilhas
    ```

### 5. Teste do erro 500 de trilhas

Este cenário testa o erro 500 que exibe quais são os níveis permitidos de cadastrado, que foram restringidos pelo ENUM **NivelTrilha**.
O erro é causado pois você está tentando cadastrar o nivel "Pleno".

* **Requisição:**
    ```http
    PUT http://localhost:8080/trilhas/6
    ```
* **Exemplo de body:**
  ```
    {
    "nome": "Liderança Consciente e Ágil",
    "descricao": "Módulos avançados em gestão de equipes remotas e estratégia.",
    "tipoTrilha": "GESTAO",
    "nivel": "PLENO",             
    "cargaHoraria": 120,
    "focoPrincipal": "liderança",  
    "ativo": true
     }
  ```

### 6. Edição da trilha

Este é o mesmo cenário do item 5, mas agora com os dados corretos.

* **Requisição:**
    ```http
    PUT http://localhost:8080/trilhas/6
    ```
* **Exemplo de body:**
  ```
    {
    "nome": "Liderança Consciente e Ágil",
    "descricao": "Módulos avançados em gestão de equipes remotas e estratégia.",
    "tipoTrilha": "GESTAO",
    "nivel": "AVANCADO",             
    "cargaHoraria": 120,
    "focoPrincipal": "liderança",  
    "ativo": true
    }
  ```


***

Desenvolvido por: 
- Bruna da Costa Candeias     RM: 558938 
- Lucas Derenze Simidu        RM: 555931 
- Sofia Fernandes             RM: 554873
