package com._p1m.productivity_suite.security.service.write;

import com._p1m.productivity_suite.config.command.CommandProcessingResult;
import com._p1m.productivity_suite.security.dto.LoginRequest;

public interface AuthWriteService {
    CommandProcessingResult login(final LoginRequest request);
}
