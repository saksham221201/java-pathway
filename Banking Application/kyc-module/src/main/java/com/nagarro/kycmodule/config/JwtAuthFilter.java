package com.nagarro.kycmodule.config;

import com.nagarro.kycmodule.client.UserServiceClient;
import com.nagarro.kycmodule.dto.User;
import com.nagarro.kycmodule.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(FeignClientConfig.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final UserServiceClient userServiceClient;

    @Autowired
    public JwtAuthFilter(JwtTokenUtil jwtTokenUtil, UserServiceClient userServiceClient){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userServiceClient = userServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        User userDetails = userServiceClient.getUserByEmail(jwtTokenUtil.getEmailFromToken(token));
        if (userDetails != null){
            String userRole = jwtTokenUtil.getRoleFromToken(token);

            // Set the authority to the userRole
            GrantedAuthority authority = new SimpleGrantedAuthority(userRole);

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, token, List.of(authority)
            );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            logger.info("Setting authentication for user: {}", userRole);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("authentication credentials in filter are {}", authentication.getCredentials());
        }else {
            logger.warn("User not found for token: {}", token);
        }

        filterChain.doFilter(request, response);
    }
}
