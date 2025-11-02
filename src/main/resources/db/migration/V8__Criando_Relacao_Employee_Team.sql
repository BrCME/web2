
CREATE TABLE employee_to_team(
	employee_id UUID NOT NULL,
	team_id UUID NOT NULL,

	PRIMARY KEY (employee_id, team_id),

	CONSTRAINT employee_id_of_employee_to_team_fk
	FOREIGN KEY (employee_id)
	REFERENCES employee(id),

	CONSTRAINT team_id_of_employee_to_team_fk
	FOREIGN KEY (team_id)
	REFERENCES team(id)
);
