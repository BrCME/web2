-- Removendo a role 'OWNER'
DELETE FROM role_to_employee
WHERE role_id = (SELECT id FROM role WHERE type = 'OWNER');

DELETE FROM role WHERE type = 'OWNER';

ALTER TYPE role_type RENAME TO role_type_old;

CREATE TYPE role_type AS ENUM ('ADMIN', 'MANAGER', 'EMPLOYEE', 'NEWCOMER');

ALTER TABLE role 
    ALTER COLUMN type TYPE role_type 
    USING type::text::role_type;

DROP TYPE role_type_old;