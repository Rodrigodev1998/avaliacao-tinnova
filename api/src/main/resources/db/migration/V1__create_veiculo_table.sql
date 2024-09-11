CREATE TABLE veiculos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    veiculo VARCHAR(255) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    cor VARCHAR(50),
    ano INTEGER,
    descricao TEXT,
    vendido BOOLEAN NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP
);
