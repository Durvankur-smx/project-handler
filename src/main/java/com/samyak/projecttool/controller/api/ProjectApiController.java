package com.samyak.projecttool.controller.api;

import com.samyak.projecttool.dto.ProjectCreateDTO;
import com.samyak.projecttool.dto.ProjectResponseDTO;
import com.samyak.projecttool.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    public ProjectApiController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Create project
     */
    @PostMapping
    public ProjectResponseDTO createProject(
            @RequestParam Long userId,
            @Valid @RequestBody ProjectCreateDTO dto
    ) {
        return projectService.createProject(userId, dto);
    }

    /**
     * Get all projects for user
     */
    @GetMapping
    public List<ProjectResponseDTO> getProjects(@RequestParam Long userId) {
        return projectService.getProjects(userId);
    }

    /**
     * Archive project
     */
    @PutMapping("/{projectId}/archive")
    public ProjectResponseDTO archiveProject(
            @RequestParam Long userId,
            @PathVariable Long projectId
    ) {
        return projectService.archiveProject(userId, projectId);
    }

    /**
     * Restore project
     */
    @PutMapping("/{projectId}/restore")
    public ProjectResponseDTO restoreProject(
            @RequestParam Long userId,
            @PathVariable Long projectId
    ) {
        return projectService.restoreProject(userId, projectId);
    }
}

//dfaf