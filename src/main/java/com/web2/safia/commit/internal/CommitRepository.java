package com.web2.safia.commit.internal;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.safia.shared.entity.Commit;

public interface CommitRepository extends JpaRepository<Commit, UUID> {
}
