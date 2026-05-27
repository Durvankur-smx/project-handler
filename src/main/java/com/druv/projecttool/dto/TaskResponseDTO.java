package com.druv.projecttool.dto;

import com.druv.projecttool.entity.enums.TaskPriority;
import com.druv.projecttool.entity.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
}
