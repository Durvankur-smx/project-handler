package com.druv.projecttool.dto;

import com.druv.projecttool.entity.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCreateDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private TaskPriority priority;
}
