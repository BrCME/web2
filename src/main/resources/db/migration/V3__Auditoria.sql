CREATE TYPE report_type AS ENUM (
    'atualizacao', 'desativacao', 'ativacao', 'remocao', 'criacao'
);

CREATE TABLE report(
	id UUID PRIMARY KEY,
	author_id UUID NOT NULL,
	summary VARCHAR(500),
	report report_type,
	created_at TIMESTAMP
);