package com.samyak.projecttool.repository;

import com.samyak.projecttool.entity.Project;
import com.samyak.projecttool.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Used for ownership validation + safe access
     */
    Optional<Project> findByIdAndOwnerId(Long id, Long ownerId);

    /**
     * Prevents access to archived projects by default
     */
    Optional<Project> findByIdAndStatus(Long id, ProjectStatus status);

    /**
     * Dashboard listing — ownership enforced
     */
    List<Project> findAllByOwnerIdAndStatus(Long ownerId, ProjectStatus status);

    /**
     * Used when ownership + state both matter
     */
    Optional<Project> findByIdAndOwnerIdAndStatus(
            Long id,
            Long ownerId,
            ProjectStatus status
    );

    /**
     * Read-only safe listing (both ACTIVE & ARCHIVED)
     */
    List<Project> findAllByOwnerId(Long ownerId);

    /**
     * Example of defensive JPQL (future dashboard.html DTO use)
     */
    @Query("""
        SELECT p
        FROM Project p
        WHERE p.ownerId = :ownerId
        AND p.status <> 'ARCHIVED'
    """)
    List<Project> findAllActiveProjectsForOwner(Long ownerId);
}
