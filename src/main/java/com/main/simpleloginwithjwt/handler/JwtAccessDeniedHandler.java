package com.main.simpleloginwithjwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.simpleloginwithjwt.dto.DefaultResponse;
import com.main.simpleloginwithjwt.helper.NullEmptyChecker;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
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
