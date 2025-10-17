
CREATE TABLE employee(
	id UUID PRIMARY KEY,
	created_by UUID,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(1023) NOT NULL,
	phone_number VARCHAR(11) NOT NULL UNIQUE,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	birth_date TIMESTAMP NOT NULL,

	CONSTRAINT employee_creator_fk
	FOREIGN KEY (created_by)
	REFERENCES employee(id)
);
