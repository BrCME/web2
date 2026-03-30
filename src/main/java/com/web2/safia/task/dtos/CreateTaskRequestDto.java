package com.web2.safia.task.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskRequestDto(
		@NotBlank(message = "Name cannot be blank") String name,
		@NotBlank(message = "Description cannot be blank") String description,
		@NotNull(message = "Project Id is required") UUID projectId,
		@Future(message = "Deadline must be in future") LocalDateTime deadLine) {
}
