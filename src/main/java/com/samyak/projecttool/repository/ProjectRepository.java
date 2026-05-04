package com.samyak.projecttool.repository;

import com.samyak.projecttool.entity.Project;
import com.samyak.projecttool.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    Optional<Project> findByIdAndOwnerId(Long id, Long ownerId);


    Optional<Project> findByIdAndStatus(Long id, ProjectStatus status);


    List<Project> findAllByOwnerIdAndStatus(Long ownerId, ProjectStatus status);


    Optional<Project> findByIdAndOwnerIdAndStatus(
            Long id,
            Long ownerId,
            ProjectStatus status
    );


    List<Project> findAllByOwnerId(Long ownerId);


    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId AND p.status <> 'ARCHIVED'")
    List<Project> findAllActiveProjectsForOwner(Long ownerId);
}
