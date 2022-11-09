package com.sonet.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class UserResponse {

    private Long id;

    private String username;

    private String role;
}
