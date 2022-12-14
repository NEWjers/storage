package com.sonet.storage.dto.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private Long id;

    @Size(min = 3, max = 20)
    private String username;

    private String role;

    @Size(min = 6, max = 40)
    private String password;
}
