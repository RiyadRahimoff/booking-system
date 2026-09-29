package com.bookflow.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null!")
    String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @NotNull(message = "Last name cannot be null!")
    String lastName;
}
