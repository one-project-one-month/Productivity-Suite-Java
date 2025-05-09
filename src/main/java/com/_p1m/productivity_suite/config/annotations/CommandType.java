package com._p1m.productivity_suite.config.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CommandType {
    String entity();
    String action();
}
