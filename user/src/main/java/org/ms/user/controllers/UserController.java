package org.ms.user.controllers;

import jakarta.validation.Valid;
import org.ms.user.dtos.UserRecordDTO;
import org.ms.user.models.UserModel;
import org.ms.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDTO dto) {
        var userModel = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
