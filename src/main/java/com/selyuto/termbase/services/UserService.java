package com.selyuto.termbase.services;

import com.selyuto.termbase.models.User;
import com.selyuto.termbase.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Long createUser(User user) {
        Date date = new Date();
        user.setDateUpdated(date);
        user.setDateCreated(date);
        userRepository.save(user);
        return user.getId();
    }

    public Long editUser(User user) {
        user.setDateUpdated(new Date());
        userRepository.save(user);
        return user.getId();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
