package com.sonet.storage.service;

import com.sonet.storage.dto.request.user.UpdateUserRequest;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.dto.response.UserResponse;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.user.ERole;
import com.sonet.storage.model.user.Role;
import com.sonet.storage.model.user.User;
import com.sonet.storage.repository.MovingRecordRepository;
import com.sonet.storage.repository.RoleRepository;
import com.sonet.storage.repository.UserRepository;
import com.sonet.storage.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sonet.storage.model.user.ERole.ROLE_ADMIN;
import static com.sonet.storage.model.user.ERole.ROLE_USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MovingRecordRepository movingRecordRepository;

    public List<UserResponse> getAllUsers(String id, String username,
                                          String role) {
        Role requiredRole = null;
        if (ROLE_ADMIN.name().equals(role)) {
            requiredRole = roleRepository.findByName(ROLE_ADMIN).orElse(null);
        }
        if (ROLE_USER.name().equals(role)) {
            requiredRole = roleRepository.findByName(ROLE_USER).orElse(null);
        }

        List<User> users = userRepository.findAll(UserSpecification.getUserSpecification(id, username, requiredRole));

        return users.stream().map(
                user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().getName().name(),
                        user.getIsArchived()
                )).collect(Collectors.toList()
        );
    }

    public List<UserResponse> getUsersPage(int page, int size, String sort, String way, String id, String username,
                                           String role) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Role requiredRole = null;
        if (ROLE_ADMIN.name().equals(role)) {
            requiredRole = roleRepository.findByName(ROLE_ADMIN).orElse(null);
        }
        if (ROLE_USER.name().equals(role)) {
            requiredRole = roleRepository.findByName(ROLE_USER).orElse(null);
        }
        Page<User> users = userRepository.findAll(UserSpecification.getUserSpecification(id, username, requiredRole), pageable);

        return users.stream().map(
                user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().getName().name(),
                        user.getIsArchived()
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

        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getPassword() != null) {
            user.setPassword(encoder.encode(updateRequest.getPassword()));
        }

        String strRole = updateRequest.getRole();

        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(userRole);
        } else {
            if ("admin".equals(strRole) || ROLE_ADMIN.name().equals(strRole)) {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRole(adminRole);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRole(userRole);
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User created successfully!"));
    }

    public ResponseEntity<?> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (isUserInvolved(id)) {
            User user = userRepository.getById(id);
            user.setIsArchived(true);
            userRepository.save(user);
        } else {
            userRepository.deleteById(id);
        }

        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    private boolean isUserInvolved(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User was not found"));
        List<MovingRecord> movingRecords = movingRecordRepository.findByUser(user);
        return !movingRecords.isEmpty();
    }
}
