
CREATE TABLE role_to_employee(
	role_id UUID,
	employee_id UUID,

	CONSTRAINT role_of_role_to_employee_fk
	FOREIGN KEY (role_id)
	REFERENCES role(id),

	CONSTRAINT employee_of_role_to_employee_fk
	FOREIGN KEY (employee_id)
	REFERENCES employee(id),

	PRIMARY KEY (employee_id, role_id)
);
