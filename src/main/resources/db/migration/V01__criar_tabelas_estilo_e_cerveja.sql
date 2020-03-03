CREATE TABLE estilo(
	codigo serial PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE cerveja(
	codigo serial PRIMARY KEY,
	sku VARCHAR(50) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	descricao TEXT NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	comissao DECIMAL(10,2) NOT NULL,
	sabor VARCHAR(50) NOT NULL,
	origem VARCHAR(50) NOT NULL,
	codigo_estilo BIGINT NOT NULL,
	FOREIGN KEY (codigo_estilo) REFERENCES estilo(codigo) 
);

INSERT INTO estilo VALUES(1,'Amber Lager');
INSERT INTO estilo VALUES(2,'Dark Lager');
INSERT INTO estilo VALUES(3,'Pale Lager');
INSERT INTO estilo VALUES(4,'Pilsner');