package com.samyak.projecttool.controller.page;

import com.samyak.projecttool.entity.User;
import com.samyak.projecttool.entity.enums.UserStatus;
import com.samyak.projecttool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PublicPageController {

    private final UserRepository userRepository;

    /* ===============================
       PUBLIC PAGES
       =============================== */

    @GetMapping("/")
    public String landing() {
        return "public/landing";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("content", "public/login");
        return "layout/auth-wrapper";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("content", "public/signup");
        return "layout/auth-wrapper";
    }

    /* ===============================
       LOGIN FLOW (UI)
       =============================== */

    @PostMapping("/login-submit")
    public String loginSubmit(@RequestParam String email) {

        System.out.println(">>> LOGIN SUBMIT HIT");
        System.out.println(">>> email = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println(">>> LOGIN FAILED: USER NOT FOUND");
                    return new RuntimeException("User not found");
                });

        System.out.println(">>> LOGIN SUCCESS: userId = " + user.getId());

        return "redirect:/dashboard?userId=" + user.getId();
    }

    /* ===============================
       SIGNUP FLOW (UI)
       =============================== */

    @PostMapping("/signup-submit")
    public String signupSubmit(@RequestParam String name,
                               @RequestParam String email) {

        System.out.println(">>> SIGNUP SUBMIT HIT");
        System.out.println(">>> name = " + name);
        System.out.println(">>> email = " + email);

        // Check if user already exists
        userRepository.findByEmail(email).ifPresent(existing -> {
            System.out.println(">>> SIGNUP FAILED: USER ALREADY EXISTS");
            throw new RuntimeException("User already exists");
        });

        // Create new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        System.out.println(">>> USER CREATED SUCCESSFULLY");
        System.out.println(">>> userId = " + savedUser.getId());

        return "redirect:/dashboard?userId=" + savedUser.getId();
    }
}
