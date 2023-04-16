package com.mflix.user;

import com.mflix.core.common.RestExceptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<Role> search(RoleSearchParams roleSearchParams) {
        return Collections.singletonList(
                roleRepository.findByName(roleSearchParams.name).orElseThrow(() -> RestExceptions.resourceNotFoundException)
        );
    }
}
