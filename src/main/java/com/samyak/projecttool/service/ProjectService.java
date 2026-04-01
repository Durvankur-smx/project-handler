package com.samyak.projecttool.service;

import com.samyak.projecttool.dto.ProjectCreateDTO;
import com.samyak.projecttool.dto.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(Long userId, ProjectCreateDTO dto);

    List<ProjectResponseDTO> getProjects(Long userId);

    ProjectResponseDTO archiveProject(Long userId, Long projectId);

    ProjectResponseDTO restoreProject(Long userId, Long projectId);

    //asdsada
}
