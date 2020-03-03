CREATE TABLE usuario (
    codigo serial PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    ativo BOOLEAN DEFAULT true,
    data_nascimento DATE
);

CREATE TABLE grupo (
    codigo serial PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE permissao (
    codigo serial PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_grupo (
    codigo_usuario INTEGER NOT NULL,
    codigo_grupo INTEGER NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_grupo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo)
);

CREATE TABLE grupo_permissao (
    codigo_grupo INTEGER NOT NULL,
    codigo_permissao INTEGER NOT NULL,
    PRIMARY KEY (codigo_grupo, codigo_permissao),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo),
    FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);