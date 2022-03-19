package com.main.simpleloginwithjwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.simpleloginwithjwt.dto.DefaultResponse;
import com.main.simpleloginwithjwt.helper.NullEmptyChecker;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7198412164912939L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        DefaultResponse response;

        final String expired = (String) httpServletRequest.getAttribute("expired");
        if (NullEmptyChecker.isNotNullOrEmpty(expired)) {
            response = new DefaultResponse(false, "Token expired, silahkan login kembali!");
        } else {
            response = new DefaultResponse(false, "Token invalid, silahkan login kembali!");
        }

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), response);
    }
}
