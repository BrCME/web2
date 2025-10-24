package com.web2.safia.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Commit extends Base {
    @NotBlank(message = "Descrição não pode ser vazia")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommitType type;

    public Commit() {
        super();
    }

    public Commit(
            @NotBlank(message = "Descrição não pode ser vazia") String description,
            CommitType type) {

        this.description = description;
        this.type = type;
    }

    public Commit(
            UUID id,
            UUID creator,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            @Valid @NotBlank(message = "Descrição não pode ser vazia") String description,
            CommitType type) {

        super(id, creator, createdAt, updatedAt, deletedAt);
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            @Valid @NotBlank(message = "Descrição não pode ser vazia") String description) {

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
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Commit [description=" + description +
                ", id=" + id +
                ", type=" + type +
                ", creator=" + creator +
                ", createdAt=" + createdAt + "]";
    }
}
