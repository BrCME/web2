package com.web2.safia.commit.internal;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.web2.safia.commit.api.CommitType;
import com.web2.safia.commit.api.event.SystemCommitOcurredEvent;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.shared.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "commit")
public class Commit extends BaseEntity {
    @Column(name = "description", nullable = false)
    private String description;

    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CommitType type;

    public Commit() {
        super();
    }

    public Commit(
            String description,
            CommitType type) {

        this.description = description;
        this.type = type;
    }

    public Commit(
            UUID id,
            Employee creator,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            String description,
            CommitType type) {

        super(id, creator, createdAt, updatedAt, deletedAt);
        this.description = description;
        this.type = type;
    }

    public Commit(SystemCommitOcurredEvent event) {
        this.description = event.description();
        this.type = event.type();
        this.creator = event.creator();
        this.createdAt = event.createdAt();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            @Valid @NotBlank(message = "Description cannot be blank") String description) {

        this.description = description;
    }

    public CommitType getType() {
        return type;
    }

    public void setType(CommitType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Commit other = (Commit) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return type == other.type;
    }

    @Override
    public String toString() {
        return "Commit [description=" + description +
                ", id=" + id +
                ", type=" + type.name() +
                ", creator=" + creator +
                ", createdAt=" + createdAt + "]";
    }
}
