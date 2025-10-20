
CREATE TABLE project(
	id UUID PRIMARY KEY,
	created_by UUID,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	team_id UUID NOT NULL,

	CONSTRAINT project_creator_fk
	FOREIGN KEY (created_by)
	REFERENCES employee(id),

	CONSTRAINT project_team_fk
	FOREIGN KEY (team_id)
	REFERENCES team(id)
);
