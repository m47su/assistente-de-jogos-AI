<img width="1366" height="399" alt="image" src="https://github.com/user-attachments/assets/4a6c080c-9ce7-4dcb-b949-980eed5c18d6" />

# 🐔🌾 Junimo AI Assistant

Uma assistente virtual inteligente e interativo feito sob medida para jogadores de **Stardew Valley**, **Minecraft** e **Terraria**! 
Este projeto utiliza **Java 21**, **Spring Boot** e **LangChain4j** para integrar a API do Google Gemini, criando um guia amigável que responde a dúvidas sobre o jogo, lembra do contexto da conversa e até anota lembretes importantes para o jogador.

## ✨ O que a IA faz?

* **Guia Especializado:** Responde a perguntas sobre o jogo com base na Wiki oficial dos jogos selecionados (receitas, dicas de mineração, etc.).
* **Memória de Conversa:** O assistente não perde o fio da meada! Graças à integração com um banco de dados H2, ele lembra do histórico da sessão atual para manter um diálogo natural.
* **Sistema de Lembretes Inteligente:** Usando a técnica de *Agent Routing*, a IA entende quando você precisa anotar algo (ex: *"Me lembre de achar cenouras amanhã"*) e gera um comando JSON invisível que o backend intercepta para salvar o lembrete diretamente no banco de dados, confirmando a ação no chat.

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 21, Spring Boot (3.5.x)
* **IA & Integração:** LangChain4j (Google AI Gemini Provider)
* **Banco de Dados:** H2 Database (In-Memory) & Spring Data JPA
* **Frontend:** HTML, CSS, JavaScript (com tipografia e imagens temáticas)
* **Gerenciador de Dependências:** Maven

## 💻 Pré-requisitos

Para rodar este projeto na sua máquina, você precisará ter instalado:

1. [Java Development Kit (JDK) 21](https://adoptium.net/)
2. [Maven](https://maven.apache.org/) (Opcional, pois o projeto inclui o `mvnw` wrapper)
3. Uma **Chave de API do Gemini** (Google AI Studio). [Você pode gerar uma aqui](https://aistudio.google.com/app/apikey).

## 🚀 Como executar o projeto

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/m47su/stardew-valley-AI.git](https://github.com/m47su/stardew-valley-AI.git)
   cd stardew-valley-AI
   ````

2.  **Configure sua API Key:**
    Abra o arquivo `src/main/resources/application.properties` e adicione sua chave de API do Gemini:

    ```properties
    gemini.api-key=SUA_CHAVE_API_AQUI
    gemini.model=gemini-flash-latest
    ```

3.  **Inicie a aplicação:**
    Pelo terminal, na raiz do projeto, execute:

    ```bash
    ./mvnw spring-boot:run
    ```

    *(No Windows, você pode usar `mvnw.cmd spring-boot:run`)*

4.  **Acesse no navegador:**
    Abra o seu navegador e acesse: [http://localhost:8083](https://www.google.com/search?q=http://localhost:8083) *(ou a porta configurada no seu console)*.

## 🎮 Como usar
 * **Selecione a IA que deseja no menu principal:** Terão três opções disponíveis: Stardew Valley, Minecraft e Terraria.
  * **Chat Livre:** Simplesmente digite sua dúvida, como *"O que a Abigail gosta de ganhar de presente?"* ou *"Como pescar um esturjão?"*.
  * **Anotar Lembretes:** Diga algo como *"Me lembre de regar as abóboras amanhã de manhã"*. A IA processará o pedido, e o sistema salvará o lembrete automaticamente no banco de dados.

## 📂 Estrutura Principal

  * `AssistantAiService`: Define os *System Prompts* e regras de comportamento da IA.
  * `AssistantController`: Endpoint REST que faz a ponte entre o frontend e o LangChain4j, interceptando ações (como salvar lembretes em JSON).
  * `ChatMessageEntity` / `ReminderEntity`: Entidades JPA para persistência de dados no H2.
