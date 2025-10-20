
CREATE TABLE work(
	id UUID PRIMARY KEY,
	description VARCHAR(511) NOT NULL,
	employee_id UUID NOT NULL,
	task_id UUID NOT NULL,
	started_at TIMESTAMP NOT NULL,
	ended_at TIMESTAMP NOT NULL,

	CONSTRAINT work_employee_fk
	FOREIGN KEY (employee_id)
	REFERENCES employee(id),

	CONSTRAINT work_task_fk
	FOREIGN KEY (task_id)
	REFERENCES task(id)
);
