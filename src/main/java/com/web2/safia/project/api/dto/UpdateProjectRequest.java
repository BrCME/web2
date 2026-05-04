package com.web2.safia.project.api.dto;

import java.util.Optional;
import java.util.UUID;

public record UpdateProjectRequest(String name, String description, Optional<UUID> teamId, Optional<UUID> managerId) {
}
