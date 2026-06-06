package com.voltx.evgenee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponseDto {
    private Long id;
    private String name;
    private String contact;
    private UserResponseDto authUser;
}
