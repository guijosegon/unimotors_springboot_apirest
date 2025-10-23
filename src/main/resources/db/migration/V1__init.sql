CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- USUÁRIOS
CREATE TABLE usuarios (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nome VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL UNIQUE,
  senha_hash VARCHAR(255) NOT NULL,
  perfil VARCHAR(16) NOT NULL CHECK (perfil IN ('ADMIN','VENDEDOR','COMPRADOR')),
  telefone VARCHAR(20),
  criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

-- LOJAS (opcional)
CREATE TABLE lojas (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nome VARCHAR(160) NOT NULL,
  cnpj VARCHAR(18) UNIQUE,
  cidade VARCHAR(80) NOT NULL,
  estado VARCHAR(2) NOT NULL
);

CREATE TABLE lojas_usuarios (
  loja_id UUID NOT NULL REFERENCES lojas(id) ON DELETE CASCADE,
  usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
  papel_na_loja VARCHAR(30),
  PRIMARY KEY (loja_id, usuario_id)
);

-- CATÁLOGO
CREATE TABLE marcas (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE modelos (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  marca_id UUID NOT NULL REFERENCES marcas(id) ON DELETE RESTRICT,
  nome VARCHAR(120) NOT NULL,
  CONSTRAINT uq_modelo UNIQUE (marca_id, nome)
);

CREATE TABLE especificacoes_veiculo (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  modelo_id UUID NOT NULL REFERENCES modelos(id) ON DELETE CASCADE,
  ano INT CHECK (ano >= 1950 AND ano <= EXTRACT(YEAR FROM NOW()) + 1),
  combustivel VARCHAR(20),
  cambio VARCHAR(20),
  carroceria VARCHAR(30),
  portas INT CHECK (portas BETWEEN 1 AND 6)
);

CREATE TABLE opcionais (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nome VARCHAR(80) NOT NULL UNIQUE
);

-- ANÚNCIOS
CREATE TABLE anuncios (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  proprietario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
  loja_id UUID REFERENCES lojas(id) ON DELETE SET NULL,
  modelo_id UUID NOT NULL REFERENCES modelos(id) ON DELETE RESTRICT,
  especificacao_id UUID REFERENCES especificacoes_veiculo(id) ON DELETE SET NULL,
  titulo VARCHAR(140) NOT NULL,
  descricao TEXT,
  preco NUMERIC(12,2) NOT NULL CHECK (preco > 0),
  status VARCHAR(12) NOT NULL CHECK (status IN ('RASCUNHO','ATIVO','RESERVADO','VENDIDO')),
  cidade VARCHAR(80) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
  atualizado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_anuncios_status ON anuncios(status);
CREATE INDEX idx_anuncios_preco ON anuncios(preco);
CREATE INDEX idx_anuncios_cidade_estado ON anuncios(cidade, estado);

CREATE TABLE imagens_anuncio (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  anuncio_id UUID NOT NULL REFERENCES anuncios(id) ON DELETE CASCADE,
  url TEXT NOT NULL,
  ordem INT DEFAULT 0
);

CREATE TABLE anuncios_opcionais (
  anuncio_id UUID NOT NULL REFERENCES anuncios(id) ON DELETE CASCADE,
  opcional_id UUID NOT NULL REFERENCES opcionais(id) ON DELETE RESTRICT,
  PRIMARY KEY (anuncio_id, opcional_id)
);

-- PROPOSTAS
CREATE TABLE propostas (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  anuncio_id UUID NOT NULL REFERENCES anuncios(id) ON DELETE CASCADE,
  comprador_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
  valor NUMERIC(12,2) NOT NULL CHECK (valor > 0),
  mensagem VARCHAR(500),
  status VARCHAR(16) NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('PENDENTE','ACEITA','RECUSADA','CONTRAPROPOSTA')),
  criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_propostas_anuncio ON propostas(anuncio_id);
CREATE INDEX idx_propostas_comprador ON propostas(comprador_id);

-- FAVORITOS
CREATE TABLE favoritos (
  usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
  anuncio_id UUID NOT NULL REFERENCES anuncios(id) ON DELETE CASCADE,
  criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (usuario_id, anuncio_id)
);
