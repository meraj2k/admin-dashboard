package com.admindashboard.admindashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admindashboard.admindashboard.entity.User;
import com.admindashboard.admindashboard.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public User getUserById(String userId) {
//        for (User user : users) {
//            if (user.getUserId().equals(userId)) {
//                return user;
//            }
//        }
//        return null;
//    }

    public User createUser(User user) {
        user.setHashKey(user.generateHashKey(user.getHashKey()));
        return userRepository.save(user);
    }

//    public User updateUser(String userId, User updatedUser) {
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            if (user.getUserId().equals(userId)) {
//                users.set(i, updatedUser);
//                return updatedUser;
//            }
//        }
//        return null;
//    }

//    public boolean deleteUser(String userId) {
//        for (User user : users) {
//            if (user.getUserId().equals(userId)) {
//                users.remove(user);
//                return true;
//            }
//        }
//        return false;
//    }
}
