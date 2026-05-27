package com.druv.projecttool.dto;

import com.druv.projecttool.entity.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDateTime createdAt;
}
