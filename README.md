# 📚 Api de Biblioteca

Esta aplicação desenvolvida em Java permite buscar e registrar livros utilizando a **API Gutendex** e armazenar as informações de forma persistente em um banco de dados **PostgreSQL**.

## 🛠️ Tecnologias Utilizadas

- **Linguagem de Programação**: Java
- **Framework**: Spring Boot
- **Gerenciamento de Dependências**: Maven
- **Banco de Dados**: PostgreSQL
- **API Externa**: [Gutendex](https://gutendex.com/)
- **Tecnologia de Persistência**: Spring Data JPA

## 📦 Dependências

 No arquivo `pom.xml` do projeto, você encontrará as seguintes dependências:

- **Spring Data JPA**: Para facilitar a integração com o banco de dados PostgreSQL.
- **PostgreSQL Driver**: Para conectar a aplicação ao banco de dados PostgreSQL.
- **Jackson Databind**: Para manipulação de dados em formato JSON

## 🌍 Funcionalidades

A aplicação consome a API **Gutendex** para realizar as seguintes operações:
- Buscar Livros por Título
- Armazenar Livros no Banco de Dados
- Listar Livros Registrados
- Listar Autores Registrados
- Listar Autores Vivos em um Ano Específico
- Listar Livros em um Idioma Específico

## 💻 Uso

![Imagem de uso da aplicação 1](src/main/java/br/com/BuscaLivros/usoImagens/img.png) <br/>
![Imagem de uso da aplicação 2](src/main/java/br/com/BuscaLivros/usoImagens/img_1.png) <br/>
![Imagem de uso da aplicação 3](src/main/java/br/com/BuscaLivros/usoImagens/img_2.png)
