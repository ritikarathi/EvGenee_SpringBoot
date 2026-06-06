package com.voltx.evgenee.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvUserRequestDto {
    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Authentication credentials are required")
    @Valid
    private UserRequestDto authUser;
}
