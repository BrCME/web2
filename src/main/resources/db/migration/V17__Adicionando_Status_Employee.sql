-- 1. Criar o tipo ENUM para o status
CREATE TYPE employee_status AS ENUM ('PENDING', 'ACTIVE', 'BLOCKED');

-- 2. Adicionar a coluna à tabela employee com o valor padrão 'PENDING'
ALTER TABLE employee 
ADD COLUMN status employee_status NOT NULL DEFAULT 'PENDING';

-- 3. (Opcional) Se você já tem dados, pode querer ativar os usuários atuais:
UPDATE employee SET status = 'ACTIVE';