package com.samyak.projecttool.dto;

import com.samyak.projecttool.entity.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCreateDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private TaskPriority priority;
}
