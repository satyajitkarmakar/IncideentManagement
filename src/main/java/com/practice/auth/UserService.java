package com.practice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDTO authenticateUser(String username, String password){
        List<User> users = getAllUsers();
        UserDTO user = new UserDTO();
        for(User u : users){
            if(u.getUserName().equals(username) && u.getPassword().equals(password)){
                user.setUserName(u.getUserName());
                user.setPassword(u.getPassword());
                user.setRole(u.getRole());
                break;
            }
        }
        return user;
    }
}
