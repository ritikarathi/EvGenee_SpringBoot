package com.voltx.evgenee.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationOwnerRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Contact details cannot be blank")
    private String contact;

    @NotNull(message = "Authentication credentials are required")
    @Valid
    private UserRequestDto authUser;
}
