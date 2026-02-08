package com.web2.safia.task.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskRequestDto(
		@Valid @NotBlank(message = "Name cannot be blank") String name,
		@Valid @NotBlank(message = "Description cannot be blank") String description,
		@Valid @NotNull(message = "Project id cannot be null") UUID projectId,
		@Valid @Future(message = "Deadline must be in future") LocalDateTime deadLine
	) {
}
