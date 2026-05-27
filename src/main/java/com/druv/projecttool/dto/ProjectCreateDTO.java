package com.druv.projecttool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectCreateDTO {

    @NotBlank(message = "Project name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 500)
    private String description;
}
