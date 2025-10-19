
CREATE TYPE commit_type AS ENUM ('ATUALIZACAO', 'DESATIVACAO', 'ATIVACAO', 'REMOCAO', 'CRIACAO');

CREATE TABLE commit(
	id UUID PRIMARY KEY,
	created_by UUID,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	description VARCHAR(1023) NOT NULL,
	type commit_type NOT NULL,

	CONSTRAINT commit_creator_fk
	FOREIGN KEY (created_by)
	REFERENCES employee(id)
);
