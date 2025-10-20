
CREATE TABLE employee_to_project(
	employee_id UUID NOT NULL,
	project_id UUID NOT NULL,

	PRIMARY KEY (employee_id, project_id),

	CONSTRAINT employee_id_of_employee_to_project_fk
	FOREIGN KEY (employee_id)
	REFERENCES employee(id),

	CONSTRAINT project_id_of_employee_to_project_fk
	FOREIGN KEY (project_id)
	REFERENCES project(id)
);
