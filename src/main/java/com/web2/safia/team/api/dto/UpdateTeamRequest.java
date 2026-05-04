package com.web2.safia.team.api.dto;

import java.util.Optional;

public record UpdateTeamRequest(Optional<String> name, Optional<String> description) {
}
