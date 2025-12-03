-- Seed de dados mock para UniMotors
-- Pode ser executado manualmente no banco para popular dados de teste.

-- USUARIOS
INSERT INTO usuarios (id, nome, email, senha_hash, perfil, telefone)
VALUES
  (uuid_generate_v4(), 'Admin Master', 'admin@unimotors.com', '$2a$10$yWXMtlmWl3lUC.Gr3BEpe.Wn8KK7D8qf1BuFdzVJt79zZ2q5TnL9K', 'ADMIN', '11999990000'),
  (uuid_generate_v4(), 'Vendedor 1',   'vendedor1@unimotors.com', '$2a$10$yWXMtlmWl3lUC.Gr3BEpe.Wn8KK7D8qf1BuFdzVJt79zZ2q5TnL9K', 'VENDEDOR', '11988880001'),
  (uuid_generate_v4(), 'Vendedor 2',   'vendedor2@unimotors.com', '$2a$10$yWXMtlmWl3lUC.Gr3BEpe.Wn8KK7D8qf1BuFdzVJt79zZ2q5TnL9K', 'VENDEDOR', '11988880002'),
  (uuid_generate_v4(), 'Comprador 1',  'comprador1@unimotors.com', '$2a$10$yWXMtlmWl3lUC.Gr3BEpe.Wn8KK7D8qf1BuFdzVJt79zZ2q5TnL9K', 'COMPRADOR', '11977770001');

-- A senha acima eh um hash BCRYPT de "123456".

-- LOJAS
INSERT INTO lojas (id, nome, cnpj, cidade, estado)
VALUES
  (uuid_generate_v4(), 'UniMotors Premium', '12.345.678/0001-90', 'São Paulo', 'SP'),
  (uuid_generate_v4(), 'UniMotors Sul',     '98.765.432/0001-10', 'Porto Alegre', 'RS');

-- MARCAS
INSERT INTO marcas (id, nome)
VALUES
  (uuid_generate_v4(), 'Toyota'),
  (uuid_generate_v4(), 'Honda'),
  (uuid_generate_v4(), 'Chevrolet');

-- MODELos (associados às marcas acima)
WITH m AS (
  SELECT id, nome FROM marcas
)
INSERT INTO modelos (id, marca_id, nome)
SELECT uuid_generate_v4(), id,
       CASE nome
         WHEN 'Toyota' THEN 'Corolla'
         WHEN 'Honda' THEN 'Civic'
         WHEN 'Chevrolet' THEN 'Onix'
       END
FROM m
WHERE nome IN ('Toyota','Honda','Chevrolet');

-- ESPECIFICACOES
WITH mo AS (
  SELECT id, nome FROM modelos
)
INSERT INTO especificacoes_veiculo (id, modelo_id, ano, combustivel, cambio, carroceria, portas)
SELECT uuid_generate_v4(), id,
       CASE nome
         WHEN 'Corolla' THEN 2022
         WHEN 'Civic'   THEN 2021
         WHEN 'Onix'    THEN 2020
       END AS ano,
       'Flex', 'Automático', 'Sedan', 4
FROM mo;

-- OPCIONAIS
INSERT INTO opcionais (id, nome)
VALUES
  (uuid_generate_v4(), 'Ar-condicionado'),
  (uuid_generate_v4(), 'Direção hidráulica'),
  (uuid_generate_v4(), 'Bancos de couro'),
  (uuid_generate_v4(), 'Controle de estabilidade'),
  (uuid_generate_v4(), 'Airbag duplo');

-- ANUNCIOS (usa alguns usuarios, lojas e modelos existentes)
WITH u AS (
  SELECT id, email FROM usuarios
),
l AS (
  SELECT id, nome FROM lojas
),
mo AS (
  SELECT id, nome FROM modelos
),
es AS (
  SELECT id, modelo_id FROM especificacoes_veiculo
)
INSERT INTO anuncios (id, proprietario_id, loja_id, modelo_id, especificacao_id, titulo, descricao, preco, status, cidade, estado)
SELECT uuid_generate_v4(),
       (SELECT id FROM u WHERE email = 'vendedor1@unimotors.com') AS proprietario_id,
       (SELECT id FROM l WHERE nome = 'UniMotors Premium') AS loja_id,
       m.id AS modelo_id,
       e.id AS especificacao_id,
       'Toyota Corolla 2022 XEi',
       'Corolla completo, único dono, revisões em dia.',
       135000.00,
       'ATIVO',
       'São Paulo',
       'SP'
FROM mo m
JOIN es e ON e.modelo_id = m.id
WHERE m.nome = 'Corolla';

-- LIGA ANUNCIO A OPCIONAIS
WITH a AS (
  SELECT id FROM anuncios LIMIT 1
),
op AS (
  SELECT id, nome FROM opcionais
)
INSERT INTO anuncios_opcionais (anuncio_id, opcional_id)
SELECT a.id,
       op.id
FROM a, op
WHERE op.nome IN ('Ar-condicionado','Direção hidráulica','Controle de estabilidade','Airbag duplo');

-- PROPOSTA MOCK
WITH a AS (
  SELECT id FROM anuncios LIMIT 1
),
c AS (
  SELECT id FROM usuarios WHERE email = 'comprador1@unimotors.com'
)
INSERT INTO propostas (id, anuncio_id, comprador_id, valor, mensagem, status)
SELECT uuid_generate_v4(), a.id, c.id,
       130000.00,
       'Tenho interesse no veículo, consigo pagar à vista.',
       'PENDENTE'
FROM a, c;

-- FAVORITO MOCK
WITH a AS (
  SELECT id FROM anuncios LIMIT 1
),
u AS (
  SELECT id FROM usuarios WHERE email = 'comprador1@unimotors.com'
)
INSERT INTO favoritos (usuario_id, anuncio_id)
SELECT u.id, a.id
FROM u, a;

