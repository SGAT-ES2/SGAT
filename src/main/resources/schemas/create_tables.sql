CREATE TABLE pacote_turistico (
    id SERIAL PRIMARY KEY,
    nome_pacote VARCHAR(80) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    duracao VARCHAR(50),
    preco NUMERIC(10, 2) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    itinerario TEXT NOT NULL,
    descricao TEXT
);

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    address TEXT,
    preferences TEXT,
    travel_count INTEGER DEFAULT 0
);
