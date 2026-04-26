package com.web2.safia.auth.api.event;

import java.util.UUID;

public record UserCreatedEvent(UUID userId, String userUsername) {
}
