
CREATE TYPE auth."user_status" AS ENUM ('PENDING', 'ACTIVE', 'BLOCKED');

CREATE TABLE auth."user"(
	id VARCHAR(127) NOT NULL PRIMARY KEY,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(1023) NOT NULL,
	status auth."user_status" NOT NULL
);

CREATE TYPE auth."role_type" AS ENUM ('ADMIN', 'MANAGER', 'EMPLOYEE', 'NEWCOMER');

CREATE TABLE auth."role"(
	id VARCHAR(127) NOT NULL PRIMARY KEY,
	type auth."role_type" NOT NULL
);

CREATE TABLE auth."role_to_user"(
	role_id VARCHAR(127) NOT NULL,
	user_id VARCHAR(127) NOT NULL,

	CONSTRAINT role_of_role_to_user_fk
	FOREIGN KEY (role_id)
	REFERENCES auth."role"(id),

	CONSTRAINT user_of_role_to_user_fk
	FOREIGN KEY (user_id)
	REFERENCES auth."user"(id),

	PRIMARY KEY (user_id, role_id)
);
