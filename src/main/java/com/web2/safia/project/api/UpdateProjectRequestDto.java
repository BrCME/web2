package com.web2.safia.project.api;

import java.util.Optional;
import java.util.UUID;

public record UpdateProjectRequestDto(
		String name,
		String description,
		Optional<UUID> teamId,
		Optional<UUID> managerId) {}
