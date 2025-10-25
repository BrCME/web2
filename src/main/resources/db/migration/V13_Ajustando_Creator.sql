-- Commit
ALTER TABLE commit
DROP CONSTRAINT commit_creator_fk;
ALTER TABLE commit
DROP COLUMN created_by;
ALTER TABLE commit
ADD created_by BYTEA;

-- Employee
ALTER TABLE employee
DROP CONSTRAINT employee_creator_fk;
ALTER TABLE employee
DROP COLUMN created_by;
ALTER TABLE employee
ADD created_by BYTEA;

-- Project
ALTER TABLE project
DROP CONSTRAINT project_creator_fk;
ALTER TABLE project
DROP COLUMN created_by;
ALTER TABLE project
ADD created_by BYTEA;

-- Task
ALTER TABLE task
DROP CONSTRAINT task_creator_fk;
ALTER TABLE task
DROP COLUMN created_by;
ALTER TABLE task
ADD created_by BYTEA;

-- Team
ALTER TABLE team
DROP CONSTRAINT team_creator_fk;
ALTER TABLE team
DROP COLUMN created_by;
ALTER TABLE team
ADD created_by BYTEA;
