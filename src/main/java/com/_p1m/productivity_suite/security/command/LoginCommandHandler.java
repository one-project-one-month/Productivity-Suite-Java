package com._p1m.productivity_suite.security.command;

import com._p1m.productivity_suite.config.annotations.CommandType;
import com._p1m.productivity_suite.config.command.CommandProcessingResult;
import com._p1m.productivity_suite.config.command.JsonCommand;
import com._p1m.productivity_suite.config.command.NewCommandSourceHandler;
import com._p1m.productivity_suite.security.dto.LoginRequest;
import com._p1m.productivity_suite.security.service.write.AuthWriteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "AUTH", action = "LOGIN")
public class LoginCommandHandler implements NewCommandSourceHandler {

    private final AuthWriteService authWriteService;

    public LoginCommandHandler(AuthWriteService authWriteService) {
        this.authWriteService = authWriteService;
    }

    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) throws JsonProcessingException {
        final LoginRequest request = command.parse(LoginRequest.class);
        return this.authWriteService.login(request);
    }
}
