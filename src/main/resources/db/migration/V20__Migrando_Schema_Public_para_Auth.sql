
INSERT INTO auth."user" (id, created_at, deleted_at, username, password)
(SELECT e.id::VARCHAR(127), e.created_at, e.deleted_at, e.email, e.password FROM public."employee" e);

INSERT INTO auth."role" (id, type)
(SELECT r.id::VARCHAR(127), r.type::VARCHAR(127)::"auth".role_type FROM public."role" r);

INSERT INTO auth."role_to_user" (user_id , role_id)
(SELECT rte.employee_id::VARCHAR(127), rte.role_id::VARCHAR(127) FROM public."role_to_employee" rte);
