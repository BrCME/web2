package com.web2.safia.team.api;

import java.util.Optional;

public record UpdateTeamRequestDto(
		Optional<String> name,
		Optional<String> description) {
}
