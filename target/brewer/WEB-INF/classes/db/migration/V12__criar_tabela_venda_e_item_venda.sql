CREATE TABLE venda(
	codigo SERIAL PRIMARY KEY,
	data_criacao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	valor_frete DECIMAL(10,2),
	valor_desconto DECIMAL(10,0),
	valor_total DECIMAL(10,2),
	status VARCHAR(30) NOT NULL,
	observacao VARCHAR(200),
	data_hora_entrega TIMESTAMP WITHOUT TIME ZONE,
	codigo_cliente INTEGER NOT NULL,
	codigo_usuario INTEGER NOT NULL,
	FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);

CREATE TABLE item_venda(
	codigo SERIAL PRIMARY KEY,
	quantidade INTEGER NOT NULL,
	valor_unitario DECIMAL(10,2) NOT NULL,
	codigo_cerveja INTEGER NOT NULL,
	codigo_venda INTEGER NOT NULL,
	FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo),
	FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
);