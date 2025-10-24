
ALTER TABLE commit
ALTER COLUMN "type" TYPE varchar(255) USING "type"::varchar(255);

ALTER TABLE commit DROP CONSTRAINT commit_creator_fk;
