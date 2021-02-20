package com.main.simpleloginwithjwt.jwt;

import com.main.simpleloginwithjwt.entity.User;
import com.main.simpleloginwithjwt.response.LoginResponse;
import com.main.simpleloginwithjwt.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.EXPIRATION_TOKEN;
import static com.main.simpleloginwithjwt.configuration.ConstantVariableConfiguration.SECRET_KEY_FOR_GENERATE_TOKEN;

@Service
public class JWTUtils {
    @Autowired
    private UserService userService;
    private final Date loginTime = new Date(System.currentTimeMillis());

    public LoginResponse createToken(HashMap<String, Object> claims, String subject) {
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

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY_FOR_GENERATE_TOKEN)
                .parseClaimsJws(token)
                .getBody();

        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    public boolean isValidateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }

    private Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }
}
