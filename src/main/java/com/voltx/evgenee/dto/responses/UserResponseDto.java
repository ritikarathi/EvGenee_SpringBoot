package com.voltx.evgenee.dto.responses;

import com.voltx.evgenee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Instant createdAt;
    private boolean enabled;
}
