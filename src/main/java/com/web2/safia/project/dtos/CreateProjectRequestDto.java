package com.web2.safia.project.dtos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record CreateProjectRequestDto(
		String name,
		String description,
		UUID teamId,
		Optional<UUID> managerId,
		List<UUID> employeesId) {}
