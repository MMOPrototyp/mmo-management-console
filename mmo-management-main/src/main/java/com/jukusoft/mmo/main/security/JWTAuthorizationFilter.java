package com.jukusoft.mmo.main.security;

import com.jukusoft.mmo.core.security.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final SessionService sessionService;

    //@Value("${jwt.header}")
    private String tokenHeader;

    //@Value("${jwt.secret}")
    private String secret;

    public JWTAuthorizationFilter(AuthenticationManager authManager, SessionService sessionService, final String tokenHeader, final String secret) {
        super(authManager);
        this.sessionService = sessionService;
        this.tokenHeader = tokenHeader;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(tokenHeader);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UserAccount authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (SignatureException | ExpiredJwtException e) {
            // custom error response class used across my project
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().write("{\"error\": \"token is invalid or has expired\"}");
            res.getWriter().flush();
            res.getWriter().close();
        }
    }

    private UserAccount getAuthentication(HttpServletRequest request) {
        String requestHeader = request.getHeader(tokenHeader);
        if (requestHeader != null) {
            //see also: https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

            //see also: https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java

            String authToken = requestHeader.substring(7);

            // parse the token
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authToken)
                    .getBody();

            String username = body.getSubject();

            return sessionService.findUser(username);
        }

        return null;
    }

    protected Boolean isTokenNotExpired(Claims body) {
        final Date expiration = body.getExpiration();
        return expiration.after(new Date());
    }

    protected boolean validateToken(Claims body) {
        return isTokenNotExpired(body);
    }

}
