# Agendamento API

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de agendamentos, aplicando os mesmos conceitos de negócio do meu projeto [Appointment Manager](https://appointment-manager-p44n.vercel.app) (originalmente em React/TypeScript/Supabase), agora implementados em backend Java puro.

🔗 **API em produção:** https://agendamento-api-krjz.onrender.com
🔗 **Frontend (React, via Lovable):** [adicionar link do frontend publicado]

## Sobre o projeto

Sistema de agendamento de serviços com três entidades principais: **Cliente**, **Serviço** e **Agendamento**. A regra de negócio central é a **validação de conflito de horário**: o sistema impede que dois agendamentos ativos ocupem o mesmo intervalo de tempo.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (banco em memória, sem necessidade de configuração externa)
- JUnit 5 + Mockito (testes unitários)
- Maven
- Deploy: Render (Docker)

## Arquitetura
```
config/        → Configuração de CORS
model/         → Entidades JPA (Cliente, Servico, Agendamento, StatusAgendamento)
repository/    → Interfaces Spring Data JPA, incluindo consulta de conflito de horário
service/       → Regra de negócio (AgendamentoService)
controller/    → Endpoints REST
exception/    → Exceções customizadas e tratamento global de erros
``` 

## Endpoints principais

| Método | Endpoint                        | Descrição                          |
| ------ | -------------------------------- | ----------------------------------- |
| GET    | /api/clientes                    | Lista clientes                      |
| POST   | /api/clientes                    | Cria cliente                        |
| GET    | /api/servicos                    | Lista serviços                      |
| POST   | /api/servicos                    | Cria serviço                        |
| GET    | /api/agendamentos                | Lista agendamentos                  |
| POST   | /api/agendamentos                | Cria agendamento (valida conflito)  |
| PATCH  | /api/agendamentos/{id}/cancelar  | Cancela um agendamento              |

## Regra de negócio: conflito de horário

Ao criar um agendamento, o `AgendamentoService` consulta o repositório para verificar se já existe algum agendamento **ativo** (não cancelado) cujo intervalo se sobreponha ao novo horário solicitado. Se houver sobreposição, uma `ConflitoHorarioException` é lançada e retornada como HTTP 409 (Conflict).

## Testes

Os testes unitários (`AgendamentoServiceTest`) cobrem:

- Criação de agendamento sem conflito
- Rejeição de agendamento com conflito de horário
- Cálculo correto do horário de término com base na duração do serviço
- Cancelamento de agendamento
- Tratamento de agendamento inexistente

Rodar os testes:
## Como rodar localmente
A aplicação sobe em `http://localhost:8080`. O console do H2 fica disponível em `http://localhost:8080/h2-console`.

## Próximos passos

- Adicionar autenticação (Spring Security)
- Migrar para PostgreSQL em produção
- Endpoint de disponibilidade de horários por serviço