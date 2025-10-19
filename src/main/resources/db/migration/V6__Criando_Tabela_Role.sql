
CREATE TYPE role_type AS ENUM ('ADMIN', 'OWNER', 'MANAGER', 'EMPLOYEE', 'NEWCOMER');

CREATE TABLE role(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	type role_type NOT NULL
);

INSERT INTO role(type) 
VALUES 
	('ADMIN'),
	('OWNER'),
	('MANAGER'),
	('EMPLOYEE'),
	('NEWCOMER');
