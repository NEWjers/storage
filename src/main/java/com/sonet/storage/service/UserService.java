package com.sonet.storage.service;

import com.sonet.storage.dto.request.UpdateUserRequest;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.dto.response.UserResponse;
import com.sonet.storage.model.user.ERole;
import com.sonet.storage.model.user.Role;
import com.sonet.storage.model.user.User;
import com.sonet.storage.repository.RoleRepository;
import com.sonet.storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sonet.storage.model.user.ERole.ROLE_ADMIN;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

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

    public ResponseEntity<?> updateUser(UpdateUserRequest updateRequest) {
        if (!userRepository.existsById(updateRequest.getId())) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        User user = userRepository.getById(updateRequest.getId());

        user.setUsername(updateRequest.getUsername());
        user.setPassword(encoder.encode(updateRequest.getPassword()));

        String strRole = updateRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            if ("admin".equals(strRole)) {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private String getMainRoleString(Set<Role> roles) {
        List<String> mainRole = new ArrayList<>();
        roles.forEach(role -> {
            if (ROLE_ADMIN.equals(role.getName())) {
                mainRole.add("admin");
            } else {
                mainRole.add("user");
            }
        });
        return mainRole.get(0);
    }
}
