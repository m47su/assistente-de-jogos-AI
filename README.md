# 🐔🌾 Games AI Assistant

Um assistente virtual inteligente e interativo feito sob medida para jogadores de **Stardew Valley**, **Minecraft** e **Terraria**! 
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
