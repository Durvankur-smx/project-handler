package com.druv.projecttool.entity;

import com.druv.projecttool.entity.enums.TaskPriority;
import com.druv.projecttool.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    /**
     * Tasks inherit authorization from project.
     */
    @Column(nullable = false)
    private Long projectId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskPriority priority;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* ===============================
       LIFECYCLE CALLBACKS
       =============================== */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = TaskStatus.OPEN;
        }
        if (this.priority == null) {
            this.priority = TaskPriority.MEDIUM;
        }
    }
}
