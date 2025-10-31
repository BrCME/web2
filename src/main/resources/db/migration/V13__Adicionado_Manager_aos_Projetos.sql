

ALTER TABLE project
ADD manager_id UUID;
ALTER TABLE project
ADD CONSTRAINT project_manager_fk FOREIGN KEY (manager_id) REFERENCES employee(id);
