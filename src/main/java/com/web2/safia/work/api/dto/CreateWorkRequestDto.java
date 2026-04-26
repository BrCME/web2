package com.web2.safia.work.api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWorkRequestDto(
		@NotBlank(message = "Description is required") String description,
		@NotNull(message = "Task Id is required") UUID taskId) {
}
