package com.web2.safia.commit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommitRepository extends JpaRepository<Commit, UUID> {

}
