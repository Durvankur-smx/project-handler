package com.druv.projecttool.service;

import com.druv.projecttool.dto.ProjectCreateDTO;
import com.druv.projecttool.dto.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(Long userId, ProjectCreateDTO dto);

    List<ProjectResponseDTO> getProjects(Long userId);

    ProjectResponseDTO archiveProject(Long userId, Long projectId);

    ProjectResponseDTO restoreProject(Long userId, Long projectId);

    //asdsada
}
