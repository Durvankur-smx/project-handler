package com.samyak.projecttool.controller.page;

import com.samyak.projecttool.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long userId, Model model) {

        System.out.println(">>> DASHBOARD HIT for userId = " + userId);

        model.addAttribute("projects",
                projectService.getProjects(userId));

        model.addAttribute("userId", userId);
        model.addAttribute("content", "dashboard/dashboard");

        return "layout/sidebar-wrapper";
    }

}
