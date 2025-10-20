
CREATE TYPE task_status AS ENUM ('TO_DO', 'DOING', 'IN_ANALYSYS', 'DONE');

CREATE TABLE task(
id UUID PRIMARY KEY,
	created_by UUID,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	name VARCHAR(255) NOT NULL,
	description VARCHAR(1023) NOT NULL,
	status task_status NOT NULL,
	dead_line TIMESTAMP NOT NULL,
	project_id UUID NOT NULL,

	CONSTRAINT task_creator_fk
	FOREIGN KEY (created_by)
	REFERENCES employee(id),

	CONSTRAINT project_task_fk
	FOREIGN KEY (project_id)
	REFERENCES project(id)
);
