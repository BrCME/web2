CREATE TYPE employee_status AS ENUM ('PENDING', 'ACTIVE', 'BLOCKED');

ALTER TABLE employee 
	ADD COLUMN status employee_status NOT NULL DEFAULT 'PENDING';

UPDATE employee SET status = 'ACTIVE';
