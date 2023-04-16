package com.mflix.user;

import com.mflix.core.common.RestExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> search(@RequestParam("username") String username) {
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> RestExceptions.resourceNotFoundException)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable String id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        userRepository.deleteById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> save(@RequestBody UserRequest saveRequest) {
        User user = new User();
        user.name = saveRequest.name;
        user.email = saveRequest.email;
        user.password = saveRequest.password;
        user.creationDate = new Date();
        user.roles = saveRequest.roles;
        return ResponseEntity.ok(userRepository.save(user));
    }
}
