package com.sonet.storage.service;

import com.sonet.storage.dto.response.UserResponse;
import com.sonet.storage.model.user.Role;
import com.sonet.storage.model.user.User;
import com.sonet.storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sonet.storage.model.user.ERole.ROLE_ADMIN;
import static com.sonet.storage.model.user.ERole.ROLE_USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(
                user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        getMainRoleString(user.getRoles())
                )).collect(Collectors.toList()
        );
    }

    private String getMainRoleString(Set<Role> roles) {
        List<String> mainRole = new ArrayList<>();
        roles.forEach(role -> {
            if (ROLE_ADMIN.equals(role.getName())) {
                mainRole.add(ROLE_ADMIN.name());
            } else {
                mainRole.add(ROLE_USER.name());
            }
        });
        return mainRole.get(0);
    }
}
