Video mostrando o sistema: https://youtu.be/PCB2VSRnDlk

# Controle-de-Usuarios
Um sistema que te permite cadastrar, buscar, alterar e excluir Usuários, assim como seus endereços

Esse é o backend do projeto, feito em Spring Boot

## ✅ Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/)
- (PostgreSql) Banco de dados configurado

## 🚀 Como rodar o projeto

1. **Clone o repositório:**
git clone https://github.com/LUC4T0N1/Controle-de-Usuarios.git
cd Controle-de-Usuarios
cd testeFCamara

2. Configure o banco de dados PostgreSql no arquivo application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
spring.datasource.username=usuario
spring.datasource.password=senha

3. Compile o projeto:
./mvnw clean install

4. Rode a apliacação:
./mvnw spring-boot:run

5. Use o projeto:
A API estará disponível em http://localhost:8080

6.Conseguir acesso:
Ao rodar o projeto, as tabelas no banco de dados serão criadas automaticamente. 
Para gerar um usuário com permissão de ADMIN para usar todo o projeto, vá no arquivo de testes CriarPrimeiroAcessoTest e rode o teste cadastrarAdminPrimeiroAcesso.
isso salvará no banco de dados um usuario com email: admin@teste.com e senha: 12345, que podera ser usado para fazer login no sistema.


