
INSERT INTO role(type)
VALUES ('ADMIN'), ('OWNER'), ('MANAGER'), ('EMPLOYEE'), ('NEWCOMER');

CREATE TABLE employee_role(
	role_id UUID,
	employee_id UUID,

	CONSTRAINT role_employee_to_role_fk
	FOREIGN KEY (role_id)
	REFERENCES role(id),

	CONSTRAINT employee_employee_to_role_fk
	FOREIGN KEY (employee_id)
	REFERENCES employee(id),

	PRIMARY KEY (employee_id, role_id)
);
