package com.druv.projecttool.controller.api;

import com.druv.projecttool.dto.TaskCreateDTO;
import com.druv.projecttool.dto.TaskResponseDTO;
import com.druv.projecttool.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    private final TaskService taskService;

    public TaskApiController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create task under project
     */
    @PostMapping
    public TaskResponseDTO createTask(
            @RequestParam Long userId,
            @RequestParam Long projectId,
            @Valid @RequestBody TaskCreateDTO dto
    ) {
        return taskService.createTask(userId, projectId, dto);
    }

    /**
     * Get tasks for project
     */
    @GetMapping
    public List<TaskResponseDTO> getTasks(
            @RequestParam Long userId,
            @RequestParam Long projectId
    ) {
        return taskService.getTasksForProject(userId, projectId);
    }

    /**
     * Archive task
     */
    @PutMapping("/{taskId}/archive")
    public TaskResponseDTO archiveTask(
            @RequestParam Long userId,
            @PathVariable Long taskId
    ) {
        return taskService.archiveTask(userId, taskId);
    }
}
