package com.bookflow.business.dto.request;

import com.bookflow.business.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateBusinessRequest(
        @NotNull(message = "Name cannot be null!")
        @NotEmpty(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Description cannot be null!")
        @NotEmpty(message = "Description cannot be empty")
        String description,

        @NotBlank(message = "Email cannot be empty")
        @NotNull(message = "Email cannot be null")
        String email,

        @NotBlank(message = "City cannot be empty")
        @NotNull(message = "City cannot be null")
        Region city,

        @NotBlank(message = "Phone cannot be empty")
        @NotNull(message = "Phone cannot be null")
        String phone,

        @NotBlank(message = "Address cannot be empty")
        @NotNull(message = "Address cannot be null")
        String address,

        @NotBlank(message = "Latitude cannot be empty")
        @NotNull(message = "Latitude cannot be null")
        BigDecimal latitude,

        @NotBlank(message = "Longitude cannot be empty")
        @NotNull(message = "Longitude cannot be null")
        BigDecimal longitude

) {
}
