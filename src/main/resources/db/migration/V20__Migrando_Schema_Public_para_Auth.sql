
INSERT INTO auth."user" (id, created_at, deleted_at, username, password)
(SELECT e.id, e.created_at, e.deleted_at, e.email, e.password FROM public."employee" e);

INSERT INTO auth."role" (id, type)
(SELECT r.id, r.type FROM public."role" r);

INSERT INTO auth."role_to_user" (user_id , role_id )
(SELECT rte.employee_id, rte.role_id FROM public."role_to_employee" rte);
