package org.roomfinder.controller;

import org.roomfinder.model.User;
import org.roomfinder.model.dto.UserDTO;
import org.roomfinder.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") UUID id, @RequestBody UserDTO user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setLatitude(user.getLatitude());
        userToUpdate.setLongitude(user.getLongitude());
        userRepository.save(userToUpdate);
    }

}
