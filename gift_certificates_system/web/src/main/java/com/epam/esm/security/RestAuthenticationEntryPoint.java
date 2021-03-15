package com.epam.esm.security;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class {@code RestAuthenticationEntryPoint} Custom REST authentication entry point.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private static final String DEFAULT_LOCALE = "en";

    private final MessageSource messageSource;

    @Autowired
    public RestAuthenticationEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        Locale locale = request.getLocale();

        if (locale == null) {
            locale = new Locale(DEFAULT_LOCALE);
        }

        String errorMessage =
                String.format(messageSource.getMessage(ExceptionKey.AUTHORIZATION_ERROR, new Object[]{}, locale),
                        authException.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, Object> data = new HashMap<>();
        data.put(ERROR_MESSAGE, errorMessage);
        data.put(ERROR_CODE, ErrorCode.AUTHORIZATION_ERROR.getCode());

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }
}
