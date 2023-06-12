package com.selyuto.termbase.controllers;

import com.selyuto.termbase.annotations.RequiredPrivileges;
import com.selyuto.termbase.enums.Privilege;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.services.UserService;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        try {
            Long id = userService.createUser(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public ResponseEntity<Long> editUser(@RequestBody User user, @PathVariable Long id) {
        try {
            userService.editUser(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/reset-password")
    @RequiredPrivileges(privileges = {Privilege.USER_MANAGER})
    public ResponseEntity<Long> resetPassword(@RequestBody User user, @PathVariable Long id) {
        try {
            userService.resetPassword(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}