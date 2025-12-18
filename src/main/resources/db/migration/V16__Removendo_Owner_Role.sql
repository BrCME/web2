-- 1. Remover associações de funcionários que possuem a role 'OWNER'
-- Se você quiser manter os funcionários e apenas tirar o cargo, use DELETE. 
-- Se quiser transformá-los em 'MANAGER', use um UPDATE antes.
DELETE FROM employee_role 
WHERE role_id = (SELECT id FROM role WHERE type = 'OWNER');

-- 2. Deletar o registro físico da tabela 'role'
DELETE FROM role WHERE type = 'OWNER';

-- 3. Atualizar o tipo ENUM (Remover 'OWNER')
-- PostgreSQL não permite "DROP VALUE" em versões antigas de forma simples, 
-- então recriamos o tipo para garantir compatibilidade:

ALTER TYPE role_type RENAME TO role_type_old;

CREATE TYPE role_type AS ENUM ('ADMIN', 'MANAGER', 'EMPLOYEE', 'NEWCOMER');

-- Atualiza a coluna da tabela para usar o novo ENUM
ALTER TABLE role 
    ALTER COLUMN type TYPE role_type 
    USING type::text::role_type;

-- Remove o tipo antigo
DROP TYPE role_type_old;