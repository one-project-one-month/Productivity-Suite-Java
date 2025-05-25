package com._p1m.productivity_suite.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com._p1m.productivity_suite.config.validators.PomodoroTimerDeserializer;
import com._p1m.productivity_suite.config.validators.PomodoroTimerSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Documented
@JsonDeserialize(using=PomodoroTimerDeserializer.class)
@JsonSerialize(using=PomodoroTimerSerializer.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
public @interface PomodoroTimerFormat {

}
