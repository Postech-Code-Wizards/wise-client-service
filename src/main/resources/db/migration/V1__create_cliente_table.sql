DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE,
    rua VARCHAR(255),
    numero VARCHAR(20),
    cep VARCHAR(20),
    cidade VARCHAR(100),
    uf VARCHAR(2)
);
