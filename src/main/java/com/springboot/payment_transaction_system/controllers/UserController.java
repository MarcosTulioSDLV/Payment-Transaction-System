package com.springboot.payment_transaction_system.controllers;

import com.springboot.payment_transaction_system.dtos.UserRequestDto;
import com.springboot.payment_transaction_system.dtos.UserResponseDto;
import com.springboot.payment_transaction_system.models.User;
import com.springboot.payment_transaction_system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/users")
    public ResponseEntity<Page<UserResponseDto>> getUsers(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @GetMapping(value = "/users-by-id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody @Valid UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.addUser(userRequestDto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserRequestDto userRequestDto,
                                                      @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(userRequestDto,id));
    }

    //Note: Not required for the assessment test
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> removeUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.removeUser(id));
    }

}
