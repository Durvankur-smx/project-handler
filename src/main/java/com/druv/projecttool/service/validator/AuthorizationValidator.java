package com.druv.projecttool.service.validator;

import com.druv.projecttool.entity.Project;
import com.druv.projecttool.entity.User;
import com.druv.projecttool.entity.enums.ProjectStatus;
import com.druv.projecttool.entity.enums.UserStatus;
import com.druv.projecttool.exception.AccessDeniedException;
import com.druv.projecttool.exception.InvalidStateException;
import com.druv.projecttool.exception.UserInactiveException;
import com.druv.projecttool.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {

    private final UserRepository userRepository;

    public AuthorizationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ===============================
       USER VALIDATION
       =============================== */

    public User validateActiveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User does not exist"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UserInactiveException("User account is not active");
        }

        return user;
    }

    /* ===============================
       PROJECT VALIDATION
       =============================== */

    public void validateProjectOwnership(Long userId, Project project) {
        if (!project.getOwnerId().equals(userId)) {
            throw new AccessDeniedException("You do not own this project");
        }
    }

    public void validateProjectIsActive(Project project) {
        if (project.getStatus() != ProjectStatus.ACTIVE) {
            throw new InvalidStateException("Project is not active");
        }
    }
}
