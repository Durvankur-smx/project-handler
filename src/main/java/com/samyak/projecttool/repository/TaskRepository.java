package com.samyak.projecttool.repository;

import com.samyak.projecttool.entity.Task;
import com.samyak.projecttool.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<Task> findByIdAndProjectId(Long id, Long projectId);

    List<Task> findAllByProjectId(Long projectId);


    List<Task> findAllByProjectIdAndStatusNot(
            Long projectId,
            TaskStatus status
    );


    Optional<Task> findByIdAndStatus(Long id, TaskStatus status);
}
