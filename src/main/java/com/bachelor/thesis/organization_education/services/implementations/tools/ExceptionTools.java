package com.bachelor.thesis.organization_education.services.implementations.tools;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class ExceptionTools {
    private ExceptionTools() {}

    public static void throwRuntimeException(
            @NonNull String message,
            @NonNull Function<String, ? extends RuntimeException> exception
    ) {
        log.error(message);
        throw exception.apply(message);
    }
}
