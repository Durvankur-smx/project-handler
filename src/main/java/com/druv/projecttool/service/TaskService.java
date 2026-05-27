package com.druv.projecttool.service;

import com.druv.projecttool.dto.TaskCreateDTO;
import com.druv.projecttool.dto.TaskResponseDTO;
import com.druv.projecttool.entity.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(Long userId, Long projectId, TaskCreateDTO dto);

    List<TaskResponseDTO> getTasksForProject(Long userId, Long projectId);

    TaskResponseDTO archiveTask(Long userId, Long taskId);

    TaskResponseDTO updateStatus(Long userId, Long taskId, TaskStatus status);

    TaskResponseDTO restoreTask(Long userId, Long taskId);

}
