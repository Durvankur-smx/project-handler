package com.druv.projecttool.service.impl;

import com.druv.projecttool.dto.TaskCreateDTO;
import com.druv.projecttool.dto.TaskResponseDTO;
import com.druv.projecttool.entity.Project;
import com.druv.projecttool.entity.Task;
import com.druv.projecttool.entity.enums.TaskStatus;
import com.druv.projecttool.exception.InvalidStateException;
import com.druv.projecttool.exception.ResourceNotFoundException;
import com.druv.projecttool.repository.ProjectRepository;
import com.druv.projecttool.repository.TaskRepository;
import com.druv.projecttool.service.TaskService;
import com.druv.projecttool.service.validator.AuthorizationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final AuthorizationValidator authorizationValidator;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            AuthorizationValidator authorizationValidator,
            ModelMapper modelMapper
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.authorizationValidator = authorizationValidator;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskResponseDTO createTask(Long userId, Long projectId, TaskCreateDTO dto) {
        authorizationValidator.validateActiveUser(userId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);
        authorizationValidator.validateProjectIsActive(project);

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setProjectId(projectId);
        task.setStatus(TaskStatus.OPEN);

        return modelMapper.map(taskRepository.save(task), TaskResponseDTO.class);
    }

    @Override
    public List<TaskResponseDTO> getTasksForProject(Long userId, Long projectId) {
        authorizationValidator.validateActiveUser(userId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);

        return taskRepository.findAllByProjectId(projectId)
                .stream()
                .map(t -> modelMapper.map(t, TaskResponseDTO.class))
                .toList();
    }

    @Override
    public TaskResponseDTO archiveTask(Long userId, Long taskId) {
        authorizationValidator.validateActiveUser(userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);

        task.setStatus(TaskStatus.ARCHIVED);
        return modelMapper.map(taskRepository.save(task), TaskResponseDTO.class);
    }

    @Override
    public TaskResponseDTO updateStatus(Long userId, Long taskId, TaskStatus status) {

        authorizationValidator.validateActiveUser(userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);

        if (task.getStatus() == TaskStatus.ARCHIVED ||
                task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Task is immutable");
        }

        task.setStatus(status);
        return modelMapper.map(taskRepository.save(task), TaskResponseDTO.class);
    }
    @Override
    public TaskResponseDTO restoreTask(Long userId, Long taskId) {

        authorizationValidator.validateActiveUser(userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        authorizationValidator.validateProjectOwnership(userId, project);

        if (task.getStatus() != TaskStatus.ARCHIVED) {
            throw new InvalidStateException("Only archived tasks can be restored");
        }

        // Restored tasks always come back as OPEN
        task.setStatus(TaskStatus.OPEN);

        return modelMapper.map(
                taskRepository.save(task),
                TaskResponseDTO.class
        );
    }

}
