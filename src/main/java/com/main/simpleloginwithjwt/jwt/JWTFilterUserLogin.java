package com.main.simpleloginwithjwt.jwt;

import com.main.simpleloginwithjwt.service.DetailUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.HEADER_AUTH;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.TOKEN_PREFIX;

@Service
public class JWTFilterUserLogin extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private DetailUserLoginService detailUserLoginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader(HEADER_AUTH);
        String username = "";
        String token = "";

        if((headerAuth != null && !headerAuth.equals("") && !headerAuth.isEmpty()) && headerAuth.startsWith(TOKEN_PREFIX)) {
            token = headerAuth.substring(7);
            username = this.jwtUtils.extractUsername(token, request);
        }

        if((username != null && !username.equals("") && !username.isEmpty()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.detailUserLoginService.loadUserByUsername(username);

            if(this.jwtUtils.isValidateToken(token, userDetails, request)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
