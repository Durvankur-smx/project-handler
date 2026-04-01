package com.samyak.projecttool.controller.api;

import com.samyak.projecttool.entity.User;
import com.samyak.projecttool.entity.enums.UserStatus;
import com.samyak.projecttool.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserRepository userRepository;

    public UserApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Simple user creation (API).
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String name,
                                           @RequestParam String email) {

        System.out.println(">>> API USER CREATE HIT");
        System.out.println(">>> name = " + name);
        System.out.println(">>> email = " + email);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setStatus(UserStatus.ACTIVE);

        User saved = userRepository.save(user);

        System.out.println(">>> API USER CREATED: id = " + saved.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        System.out.println(">>> API GET USER: id = " + id);

        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
