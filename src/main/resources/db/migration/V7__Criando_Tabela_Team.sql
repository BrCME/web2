
CREATE TABLE team(
	id UUID PRIMARY KEY,
	created_by UUID,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	name VARCHAR(255) NOT NULL,
	description VARCHAR(1023) NOT NULL,

	CONSTRAINT team_creator_fk
	FOREIGN KEY (created_by)
	REFERENCES employee(id)
);
