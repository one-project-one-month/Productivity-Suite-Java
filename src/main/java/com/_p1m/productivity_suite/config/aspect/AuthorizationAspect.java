package com._p1m.productivity_suite.config.aspect;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.note_taking.repository.NoteRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final CategoryRepository categoryRepository;
    private final NoteRepository noteRepository;
    private final UserUtil userUtil;

    @Before("@annotation(authorizationCheck)")
    public void checkAuthorization(JoinPoint joinPoint, AuthorizationCheck authorizationCheck) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String authHeader = request.getHeader("Authorization");
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);

        final Long resourceId = getResourceId(joinPoint, authorizationCheck);

        switch (authorizationCheck.resource()) {
            case "CATEGORY" -> {
                final Category category = categoryRepository.findById(resourceId)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
                if (!category.getUser().getId().equals(userDto.getId())) {
                    throw new UnauthorizedException("Unauthorized to access this category");
                }
            }

            case "NOTE" -> {
                final Note note = noteRepository.findById(resourceId)
                        .orElseThrow(() -> new EntityNotFoundException("Note not found"));
                if (!note.getUser().getId().equals(userDto.getId())){
                    throw new UnauthorizedException("Unauthorized to access this note");
                }
            }

            default -> throw new IllegalArgumentException("Unsupported resource type: " + authorizationCheck.resource());
        }
    }

    private @NotNull Long getResourceId(JoinPoint joinPoint, AuthorizationCheck authorizationCheck) {
        final Object[] args = joinPoint.getArgs();
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String idParamName = authorizationCheck.idParam();

        Long resourceId = null;
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(idParamName)) {
                resourceId = (Long) args[i];
                break;
            }
        }

        if (resourceId == null) {
            throw new IllegalArgumentException("Missing resource ID for authorization check");
        }
        return resourceId;
    }
}
