package com.druv.projecttool.service.impl;

import com.druv.projecttool.dto.ProjectCreateDTO;
import com.druv.projecttool.dto.ProjectResponseDTO;
import com.druv.projecttool.entity.Project;
import com.druv.projecttool.entity.enums.ProjectStatus;
import com.druv.projecttool.exception.ResourceNotFoundException;
import com.druv.projecttool.repository.ProjectRepository;
import com.druv.projecttool.service.ProjectService;
import com.druv.projecttool.service.validator.AuthorizationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AuthorizationValidator authorizationValidator;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            AuthorizationValidator authorizationValidator,
            ModelMapper modelMapper
    ) {
        this.projectRepository = projectRepository;
        this.authorizationValidator = authorizationValidator;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectResponseDTO createProject(Long userId, ProjectCreateDTO dto) {
        authorizationValidator.validateActiveUser(userId);

        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwnerId(userId);
        project.setStatus(ProjectStatus.ACTIVE);

        Project saved = projectRepository.save(project);
        return modelMapper.map(saved, ProjectResponseDTO.class);
    }

    @Override
    public List<ProjectResponseDTO> getProjects(Long userId) {
        authorizationValidator.validateActiveUser(userId);

        return projectRepository.findAllByOwnerId(userId)
                .stream()
                .map(p -> modelMapper.map(p, ProjectResponseDTO.class))
                .toList();
    }

    @Override
    public ProjectResponseDTO archiveProject(Long userId, Long projectId) {
        authorizationValidator.validateActiveUser(userId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);
        authorizationValidator.validateProjectIsActive(project);

        project.setStatus(ProjectStatus.ARCHIVED);
        project.setArchivedAt(LocalDateTime.now());

        return modelMapper.map(projectRepository.save(project), ProjectResponseDTO.class);
    }

    @Override
    public ProjectResponseDTO restoreProject(Long userId, Long projectId) {
        authorizationValidator.validateActiveUser(userId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);

        project.setStatus(ProjectStatus.ACTIVE);
        project.setArchivedAt(null);

        return modelMapper.map(projectRepository.save(project), ProjectResponseDTO.class);
    }
}
