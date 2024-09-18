package com.practice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/authenticate")
@CrossOrigin("http://localhost:4200/")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> authenticate(@RequestBody UserRequest userRequest){
        UserDTO userDTO = service.authenticateUser(userRequest.getUserName(), userRequest.getPassword());
        System.out.println(userDTO);
        if(userDTO.getUserName() != null){
            return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
