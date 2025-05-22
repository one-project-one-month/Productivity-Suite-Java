package com._p1m.productivity_suite.security.filter;

import com._p1m.productivity_suite.data.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    public Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (User) auth.getPrincipal();
        return userPrincipal.getId();
    }
}
