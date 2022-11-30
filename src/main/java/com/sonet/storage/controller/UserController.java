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

    @GetMapping("page")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsersPage(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size,
                                           @RequestParam(name = "sort") String sort,
                                           @RequestParam(name = "way") String way,
                                           @RequestParam(name = "id") String id,
                                           @RequestParam(name = "username") String username,
                                           @RequestParam(name = "role") String role) {
        return userService.getUsersPage(page, size, sort, way, id, username, role);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer getUsersNumber(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "username") String username,
                                  @RequestParam(name = "role") String role) {
        return userService.getAllUsers(id, username, role).size();
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
