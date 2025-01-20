<center>
  <p align="center">
    <img src="https://www.telecomrevieweurope.com/wp-content/uploads/2024/07/Ericsson_Faces_Additional_USD_1_Billion_Impairment_Charge_ion_Vonage_Acquisition_.jpg" />&nbsp;
  </p>  
  <h1 align="center">🚀 Game Manager</h1>
  <p align="center">
    Aplicação referente ao teste técnico da Ericsson <br />
    Utilizando Clean Architecture, DDD e boas práticas
  </p>
</center>
<br />

## Schema
``` sql
CREATE TABLE game (
    id CHAR(36) NOT NULL PRIMARY KEY,
    publisher_id VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE time_played (
    id INT AUTO_INCREMENT PRIMARY KEY,
    game_id CHAR(36) NOT NULL,
    date DATE NOT NULL,
    time INT NOT NULL, -- Time played in hours
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE,
    UNIQUE (game_id, date)
);
```

## Ferramentas necessárias

- JDK 21
- IDE de sua preferência
- Docker

## Como executar?

1. Clonar o repositório:
```sh
git clone https://github.com/Vinnissaum/game-manager.git
```

2. Subir as aplicações juntamente com Docker:
```shell
docker-compose up
```

### Para facilitar, faça o download e importe a coleção do [Postman](https://www.postman.com/downloads/) utilizada em desenvolvimento. [Download](./game-manager.postman_collection.json)
