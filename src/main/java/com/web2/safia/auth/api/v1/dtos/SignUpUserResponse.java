package com.web2.safia.auth.api.v1.dtos;

import com.web2.safia.shared.vo.UserId;

public record SignUpUserResponse(UserId id, String username) {
}
