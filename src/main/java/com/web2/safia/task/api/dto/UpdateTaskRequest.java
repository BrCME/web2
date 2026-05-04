package com.web2.safia.task.api.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UpdateTaskRequest(
		Optional<String> name,
		Optional<String> description,
		Optional<UUID> projectId,
		Optional<LocalDateTime> deadLine) {
}
