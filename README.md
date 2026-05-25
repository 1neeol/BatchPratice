# OmniChannel Message Hub - Laboratório de Performance JPA

Este projeto é um laboratório prático e um Desafio de Arquitetura desenvolvido para consolidar conhecimentos avançados em Spring Data JPA, Alta Performance de Banco de Dados e Padrões de Projeto.

O cenário simula o motor central (Core) de um SaaS de comunicação multicanal responsável por receber grandes volumes de mensagens e gerar relatórios analíticos dinâmicos de campanhas.

---

## Objetivo Principal

O foco absoluto deste projeto não foi construir uma API web genérica, mas sim explorar os limites e as melhores práticas do Spring Data JPA e do Hibernate.

O projeto resolve dois grandes problemas de sistemas corporativos:

### Estouro de Memória em Inserções em Massa
Gerenciamento do Persistence Context para lidar com a ingestão de milhares de registros (Batch Processing).

### Consultas Pesadas e Dinâmicas
Extração de relatórios analíticos utilizando agregação diretamente no banco de dados, sem trafegar entidades desnecessárias para a memória da JVM.

---

## Tecnologias e Técnicas Aplicadas

- Java 17+
- Spring Boot 3 (Web, Data JPA)
- PostgreSQL (Lidando com UUIDs e JSONB)

### Design Patterns
Aplicação do padrão Strategy/Factory junto à Injeção de Dependências do Spring para roteamento dinâmico de mensagens (WhatsApp, SMS, Email), eliminando o uso de `switch` ou `if/else` e respeitando o Open/Closed Principle do SOLID.

### JPA Criteria API & Specifications
Construção de consultas orientadas a objetos para filtros dinâmicos e paginação.

### JPQL Dinâmico
Uso de Constructor Expressions (`SELECT new DTO(...)`) para projeção direta de dados, evitando o problema de carregamento e gerenciamento de `@Entity` na memória.

### Batch Inserts
Controle manual de transações (`em.flush()` e `em.clear()`) para inserção performática de dados em lote.

---

## Endpoints Implementados

### POST `/api/v1/messages/batch`
Recebe um grande volume de mensagens agrupadas por campanha e realiza o roteamento para as interfaces corretas e a persistência otimizada no banco em lotes (chunks).

### GET `/api/v1/analytics/criteria/{campaignId}`
Retorna os totais agrupados por Canal e Status utilizando a Criteria API, aceitando os parâmetros opcionais:
- `channel`
- `startDate`
- `endDate`

### GET `/api/v1/analytics/jpql/{campaignId}`
Entrega o mesmo resultado de negócio do endpoint anterior, porém implementado através de JPQL Dinâmico direto no repositório.

---

## Escopo e Simplificações (O que foi ignorado)

Como este repositório é estritamente um laboratório de estudos focado em Persistência de Dados e Arquitetura, algumas práticas comuns ao desenvolvimento de APIs foram intencionalmente deixadas de fora do escopo:

### Validações de Entrada (Bean Validation)
Anotações como `@NotNull`, `@NotBlank` ou validações complexas de payload não foram implementadas. O foco foi o processamento pós-recebimento.

### Tratamento de Exceções de HTTP
Não há um `@ControllerAdvice` mapeado para tratar erros de formatação de URL (ex: falhas de parse de Data ou parâmetros nulos de requisição).

O foco do tratamento de `nulls` ocorreu na camada do banco de dados (ignorando parâmetros nulos na query).

### Segurança (Autenticação/Autorização)
O Spring Security não foi acoplado ao projeto.

### Integração Real de Mensageria
As classes que implementam a estratégia de envio (`WhatsAppChannel`, `SmsChannel`, etc.) realizam processamentos simulados (Mocks).

A integração com provedores reais (Twilio, AWS SES) foge do escopo do estudo de banco de dados.

---

# Como testar localmente

## 1. Configuração inicial

1. Clone o repositório.
2. Suba uma instância do PostgreSQL.
3. Configure as credenciais no `application.properties`.
4. Execute a aplicação Spring Boot.

---

## 2. Realizar o Batch Insert via API

Utilize o arquivo `message_logs_100k.json` com os **100.000 registros** para disparar contra o endpoint da aplicação e testar o particionamento em lote no Java.

---

## Como executar usando o Postman

1. Abra o Postman e crie uma nova requisição.
2. Altere o método para `POST`.
3. Insira a URL do endpoint local:

```bash
http://localhost:8080/api/v1/messages/batch
```

4. Vá até a aba **Body**.
5. Selecione a opção **binary** (ou equivalente para upload de arquivo).
6. Faça o upload do arquivo `message_logs_100k.json`.
7. Certifique-se de que o cabeçalho (**Headers**) `Content-Type` está definido como:

```bash
application/json
```

8. Clique em **Send**.

---

## Como executar via cURL (Alternativa rápida via terminal)

```bash
curl -X POST http://localhost:8080/api/v1/messages/batch \
     -H "Content-Type: application/json" \
     -d @message_logs_100k.json
```

---

## Testes Analíticos

Após popular o banco de dados, utilize os endpoints de analytics variando os filtros dinâmicos para avaliar:

- Tempo de resposta
- Consumo de memória
- Performance de agregações
- Impacto das consultas dinâmicas

Exemplos:

### Criteria API

```bash
GET /api/v1/analytics/criteria/{campaignId}
```

### JPQL Dinâmico

```bash
GET /api/v1/analytics/jpql/{campaignId}
```

Parâmetros opcionais disponíveis:

```bash
?channel=WHATSAPP
&startDate=2026-01-01
&endDate=2026-01-31
```

---

# Objetivo do Laboratório

Este projeto foi desenvolvido exclusivamente como uma prova de conceito para estudos avançados de:

- Arquitetura Backend
- Alta Performance em Java
- Persistência de Dados com JPA/Hibernate
- Batch Processing
- Consultas Dinâmicas
- Estratégias de Escalabilidade em aplicações corporativas

---

## Autor

Desenvolvido como prova de conceito para estudos de arquitetura backend e alta performance em Java.
