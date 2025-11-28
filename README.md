# ✈️ TravelManager: Sistema de Gestão de Agência de Turismo

O **TravelManager** é um sistema de gestão desenvolvido para agências de turismo, com o objetivo de otimizar e automatizar o gerenciamento de pacotes turísticos, clientes, reservas, pagamentos e itinerários. O sistema proporciona maior organização, agilidade e confiabilidade na gestão da agência.

---

## 🎯 Objetivo do Projeto

Desenvolver um sistema robusto com uma **interface intuitiva e amigável** que permita aos funcionários da agência gerenciarem todo o fluxo de trabalho, desde o cadastro de um novo pacote até a emissão do itinerário e relatórios gerenciais.

---

## ✨ Funcionalidades Principais

O sistema foi concebido com os seguintes requisitos funcionais em mente, garantindo uma gestão completa da agência:

* **Cadastro de Pacotes Turísticos:** Permite adicionar informações detalhadas sobre os pacotes, incluindo destino, datas disponíveis, itinerário, preço e descrição.
* **Cadastro de Clientes:** Gerenciamento de informações do cliente, como nome, contato, histórico de viagens e preferências.
* **Reservas de Pacotes:** Registro de novas reservas de pacotes turísticos para os clientes, associando as informações do cliente ao pacote escolhido e às datas de viagem.
* **Gerenciamento de Pagamentos:** Registro de pagamentos (totais ou parciais) feitos pelos clientes e cálculo de valores devidos. O sistema também gera recibos.
* **Geração de Itinerários:** Criação de roteiros detalhados para cada reserva, incluindo datas, horários, voos, passeios e acomodações.
* **Relatórios e Dashboard:** Geração de relatórios gerenciais (reservas, pagamentos, pacotes mais populares, clientes frequentes) e o Dashboard exibe uma visualização rápida do estado da agência.

---

## 💻 Tecnologias Utilizadas

O sistema será implementado com as seguintes tecnologias:

| Categoria | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Linguagem** | **Java** | Linguagem de programação principal. |
| **Banco de Dados** | **SGBD Relacional** | Será utilizado um sistema de gerenciamento de banco de dados relacional (por exemplo, MySQL ou PostgreSQL). |

---

## 🛡️ Requisitos Não Funcionais (Qualidade)

O projeto prioriza os seguintes requisitos de qualidade:

* **Interface de Usuário (UI):** O sistema deve ter uma interface de usuário **intuitiva e amigável**.
* **Desempenho:** O sistema deve ser **responsivo**, mesmo quando há um grande número de reservas e clientes.
* **Segurança:** O sistema deve proteger os dados dos clientes e as informações financeiras, garantindo a **autenticação segura** e o **armazenamento criptografado** das senhas.
* **Compatibilidade:** O software deve ser **compatível com sistemas operacionais Windows**.
* **Documentação:** Deve ser fornecida **documentação abrangente**, que inclua um manual do usuário e documentação técnica para os desenvolvedores.

---

## ⚙️ Instalação e Configuração

Para configurar o ambiente de desenvolvimento/execução, siga os passos abaixo:

### Pré-requisitos

1.  **JDK (Java Development Kit)** instalado (versão compatível com o projeto).
2.  Um **Sistema Gerenciador de Banco de Dados Relacional** (MySQL ou PostgreSQL) instalado e configurado.
3.  Uma **IDE Java** (como IntelliJ IDEA ou Eclipse).

### Passos de Instalação

1.  **Clone o Repositório:**
    ```bash
    git clone [LINK DO REPOSITÓRIO AQUI]
    cd travelmanager
    ```
2.  **Configuração do Banco de Dados:**
    * Crie um banco de dados vazio (ex: `travelmanager`).
    * Execute os scripts SQL (a serem fornecidos) para criar as tabelas necessárias (clientes, pacotes, reservas, etc.).
    * Atualize o arquivo de configuração de conexão do projeto com as credenciais do seu SGBD (host, usuário e senha).
3.  **Execução do Projeto:**
    * Abra o projeto na sua IDE.
    * Rode o projeto a partir da classe principal.

---

## 🧑‍💻 Equipe de Desenvolvimento

O projeto foi desenvolvido por alunos do Curso de Engenharia da Computação da **UNIVASF**.

* **Éricles Barros de Sá** (Scrum Master, Desenvolvedor): Responsável pela Atualização do Diagrama de Requisitos e Versão Final do Documento de Requisitos.
* **Heitor Freire Alves** (Product Owner, Desenvolvedor): Responsável pela Versão Inicial e Versão Final do Documento de Requisitos.
* **Francisco Sérgio Feitosa Lima Segundo** (Desenvolvedor).
* **Lucas Emanoel Gomes Ferraz** (Desenvolvedor).
* **Kaique Rangel Da Silva** (Desenvolvedor).

---

## 📄 Acesso e Uso

O acesso ao sistema é feito através da tela de **login**, onde o funcionário deve inserir suas credenciais (e-mail/usuário e senha) nos campos indicados.



Se os dados estiverem corretos, o funcionário será direcionado para a tela principal (**Dashboard**). Se as credenciais forem inválidas, o sistema exibirá uma mensagem de erro ("Usuário ou senha inválidos").

Para obter detalhes sobre o uso de cada funcionalidade (Clientes, Pacotes, Reservas, Pagamentos, Itinerários e Relatórios), consulte o **MANUAL DO USUÁRIO**.
