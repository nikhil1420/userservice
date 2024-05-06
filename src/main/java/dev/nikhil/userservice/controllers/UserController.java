package dev.nikhil.userservice.controllers;

import dev.nikhil.userservice.dtos.UserDto;
import dev.nikhil.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId) {
//        UserDto userDto = userService.getUserDetails(userId);
//
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }
//
//    @PostMapping("/{id}/roles")
//    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request) {
//
//        UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());
//
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }
}

