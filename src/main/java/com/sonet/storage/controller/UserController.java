package com.sonet.storage.controller;

import com.sonet.storage.dto.request.user.UpdateUserRequest;
import com.sonet.storage.dto.response.UserResponse;
import com.sonet.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsersPage(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size,
                                           @RequestParam(name = "sort") String sort,
                                           @RequestParam(name = "way") String way) {
        return userService.getUsersPage(page, size, sort, way);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer getUsersNumber() {
        return userService.getAllUsers().size();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateRequest) {
        return userService.updateUser(updateRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
