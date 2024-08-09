# Sistema de Agendamento de Reservas para Restaurante

# Sobre o projeto

Este projeto é um sistema de agendamento de reservas para um restaurante, desenvolvido em Java, utilizando MySQL como banco de dados e JDBC para conexão e manipulação dos dados. O sistema permite realizar operações CRUD tanto para as mesas do restaurante quanto para as reservas, garantindo que todas as exceções sejam tratadas adequadamente para oferecer uma experiência segura e livre de erros ao usuário.

# Tecnologias utilizadas
- Java
- JDBC
- MySQL
  
# Como executar o projeto

## Pré-requisitos
- JDK
- MySQL

```bash
# clonar repositório
git clone https://github.com/GabrielApolinario08/Reserva_mesas.git

# Configure usuário e senha do banco de dados no arquivo de conexão JDBC dentro do projeto (db.properties):
user=seu_usuario
password=sua_senha
dburl=jdbc:mysql://localhost:3306/reserva_mesas
useSSL=false
databaseName=reserva_mesas
table1Name=reservation
table2Name=restauranttable 

# Compile e rode o projeto na sua IDE.
```

# Autor

Gabriel Apolinário Fabrício

www.linkedin.com/in/gabrielapolinariofabricio
