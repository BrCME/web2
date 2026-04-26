package com.web2.safia.team.api.dto;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CreateTeamRequestDto(
	@NotBlank(message = "Name is required")
	String name,
	
	@NotBlank(message = "Description is required")
	String description,
	
	Set<UUID> employeesId,
	Set<UUID> projectsId
) {}
