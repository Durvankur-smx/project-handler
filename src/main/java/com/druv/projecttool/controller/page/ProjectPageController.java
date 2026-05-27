package com.druv.projecttool.controller.page;

import com.druv.projecttool.dto.ProjectCreateDTO;
import com.druv.projecttool.dto.TaskCreateDTO;
import com.druv.projecttool.entity.enums.TaskStatus;
import com.druv.projecttool.service.ProjectService;
import com.druv.projecttool.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProjectPageController {

    private final ProjectService projectService;
    private final TaskService taskService;

    /* ===============================
       PROJECT CREATE PAGE
       =============================== */

    @GetMapping("/projects/create")
    public String createProjectPage(@RequestParam Long userId, Model model) {

        System.out.println(">>> PROJECT CREATE PAGE HIT userId = " + userId);

        model.addAttribute("project", new ProjectCreateDTO());
        model.addAttribute("userId", userId);
        model.addAttribute("content", "project/project-create");

        return "layout/sidebar-wrapper";
    }

    /* ===============================
       PROJECT CREATE SUBMIT
       =============================== */

    @PostMapping("/projects/create")
    public String createProject(@RequestParam Long userId,
                                @Valid ProjectCreateDTO project,
                                BindingResult result,
                                Model model) {

        System.out.println(">>> PROJECT CREATE SUBMIT userId = " + userId);

        if (result.hasErrors()) {
            System.out.println(">>> PROJECT CREATE VALIDATION FAILED");

            model.addAttribute("userId", userId);
            model.addAttribute("content", "project/project-create");
            return "layout/sidebar-wrapper";
        }

        projectService.createProject(userId, project);

        System.out.println(">>> PROJECT CREATED SUCCESSFULLY");

        return "redirect:/dashboard?userId=" + userId;
    }

    @GetMapping("/projects")
    public String listProjects(@RequestParam Long userId, Model model) {

        System.out.println(">>> PROJECT LIST PAGE HIT userId = " + userId);

        model.addAttribute("projects", projectService.getProjects(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("content", "project/project-list");

        return "layout/sidebar-wrapper";
    }


    /* ===============================
       PROJECT DETAILS PAGE
       =============================== */

    @GetMapping("/projects/details")
    public String projectDetails(@RequestParam Long userId,
                                 @RequestParam Long projectId,
                                 Model model) {

        System.out.println(">>> PROJECT DETAILS PAGE HIT userId=" + userId +
                ", projectId=" + projectId);

        model.addAttribute("tasks",
                taskService.getTasksForProject(userId, projectId));

        model.addAttribute("task", new TaskCreateDTO());
        model.addAttribute("userId", userId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("content", "project/project-details");

        return "layout/sidebar-wrapper";
    }

    /* ===============================
       TASK CREATE (UI)
       =============================== */

    @PostMapping("/projects/tasks/create")
    public String createTask(@RequestParam Long userId,
                             @RequestParam Long projectId,
                             @Valid @ModelAttribute("task") TaskCreateDTO task,
                             BindingResult result,
                             Model model) {

        System.out.println(">>> TASK CREATE SUBMIT projectId=" + projectId);

        if (result.hasErrors()) {
            model.addAttribute("tasks",
                    taskService.getTasksForProject(userId, projectId));
            model.addAttribute("userId", userId);
            model.addAttribute("projectId", projectId);
            model.addAttribute("content", "project/project-details");
            return "layout/sidebar-wrapper";
        }

        taskService.createTask(userId, projectId, task);

        return "redirect:/projects/details?userId=" + userId +
                "&projectId=" + projectId;
    }

    /* ===============================
       TASK ARCHIVE (UI)
       =============================== */

    @PostMapping("/projects/tasks/archive")
    public String archiveTask(@RequestParam Long userId,
                              @RequestParam Long projectId,
                              @RequestParam Long taskId) {

        System.out.println(">>> TASK ARCHIVE taskId=" + taskId);

        taskService.archiveTask(userId, taskId);

        return "redirect:/projects/details?userId=" + userId +
                "&projectId=" + projectId;
    }

    @PostMapping("/projects/archive")
    public String archiveProject(@RequestParam Long userId,
                                 @RequestParam Long projectId) {

        System.out.println(">>> PROJECT ARCHIVE projectId=" + projectId);

        projectService.archiveProject(userId, projectId);

        return "redirect:/projects?userId=" + userId;
    }

    @PostMapping("/projects/restore")
    public String restoreProject(@RequestParam Long userId,
                                 @RequestParam Long projectId) {

        System.out.println(">>> PROJECT RESTORE projectId=" + projectId);

        projectService.restoreProject(userId, projectId);

        return "redirect:/projects?userId=" + userId;
    }
    @PostMapping("/projects/tasks/status")
    public String updateTaskStatus(@RequestParam Long userId,
                                   @RequestParam Long projectId,
                                   @RequestParam Long taskId,
                                   @RequestParam String status) {

        taskService.updateStatus(
                userId,
                taskId,
                Enum.valueOf(TaskStatus.class, status)
        );

        return "redirect:/projects/details?userId=" + userId +
                "&projectId=" + projectId;
    }
    @PostMapping("/projects/tasks/restore")
    public String restoreTask(@RequestParam Long userId,
                              @RequestParam Long projectId,
                              @RequestParam Long taskId) {

        System.out.println(">>> TASK RESTORE taskId=" + taskId);

        taskService.restoreTask(userId, taskId);

        return "redirect:/projects/details?userId=" + userId +
                "&projectId=" + projectId;
    }



}
