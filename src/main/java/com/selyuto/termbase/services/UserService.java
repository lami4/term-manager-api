package com.selyuto.termbase.services;

import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.enums.Status;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final Authenticator authenticator;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, Authenticator authenticator) {
        this.userRepository = userRepository;
        this.authenticator = authenticator;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Long createUser(User user) {
        Date date = new Date();
        user.setDateUpdated(date);
        user.setDateCreated(date);
        userRepository.save(user);
        return user.getId();
    }

    public Long editUser(User user) {
        if (user.getStatus() == Status.BLOCKED) {
            voidUserSession(user);
        }
        user.setDateUpdated(new Date());
        userRepository.save(user);
        return user.getId();
    }

    private void voidUserSession(User user) {
        authenticator.voidSessionByUserId(user.getId());
    }

    public void resetPassword(User user) {
        userRepository.resetPassword(user.getPassword(), user.getId());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
