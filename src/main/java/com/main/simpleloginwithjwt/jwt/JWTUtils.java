package com.main.simpleloginwithjwt.jwt;

import com.main.simpleloginwithjwt.entity.User;
import com.main.simpleloginwithjwt.response.LoginResponse;
import com.main.simpleloginwithjwt.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.EXPIRATION_TOKEN;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.SECRET_KEY_FOR_GENERATE_TOKEN;

@Service
public class JWTUtils {
    private final UserService userService;

    private final Date loginTime = new Date(System.currentTimeMillis());

    public JWTUtils(UserService userService) {
        this.userService = userService;
    }

    private LoginResponse createToken(HashMap<String, Object> claims, String subject) {
        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(loginTime)
                .setAudience((String) claims.get("name"))
                .setExpiration(new Date(loginTime.getTime() + EXPIRATION_TOKEN))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_FOR_GENERATE_TOKEN)
                .compact();

        LoginResponse response = new LoginResponse();
        response.setStatusCode(200);
        response.setSuccess(true);
        response.setMessage("Login Success!");
        response.setToken(token);

        return response;
    }

    public LoginResponse generateToken(String username) {
        User user = userService.findByUsername(username);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getUsername());
        claims.put("age", user.getAge());
        claims.put("username", user.getUsername());

        return this.createToken(claims, user.getUsername());
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction, HttpServletRequest request) {
        try {
            final Claims claims = Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY_FOR_GENERATE_TOKEN)
                    .parseClaimsJws(token)
                    .getBody();
            return claimsTFunction.apply(claims);
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
            request.setAttribute("invalid", ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
            request.setAttribute("invalid", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            request.setAttribute("expired", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT exception");
            request.setAttribute("invalid", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Jwt claims string is empty");
            request.setAttribute("invalid", ex.getMessage());
        }

        return null;
    }

    String extractUsername(String token, HttpServletRequest request) {
        return this.extractClaims(token, Claims::getSubject, request);
    }

    boolean isValidateToken(String token, UserDetails userDetails, HttpServletRequest request) {
        final String username = this.extractUsername(token, request);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token, request));
    }

    private Date extractExpiration(String token, HttpServletRequest request) {
        return this.extractClaims(token, Claims::getExpiration, request);
    }

    private boolean isTokenExpired(String token, HttpServletRequest request) {
        return this.extractExpiration(token, request).before(new Date());
    }
}
