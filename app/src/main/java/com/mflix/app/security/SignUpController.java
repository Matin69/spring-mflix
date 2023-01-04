package com.mflix.app.security;

import com.mflix.app.user.User;
import com.mflix.app.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignUpController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public boolean saveUser(@RequestBody SignUpRequest signUpRequest) {
        User user = new User();
        user.name = signUpRequest.name;
        user.email = signUpRequest.email;
        user.password = passwordEncoder.encode(signUpRequest.password);
        userRepository.save(user);
        return true;
    }
}
