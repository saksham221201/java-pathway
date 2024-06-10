package com.nagarro.usermodule.config;

import com.nagarro.usermodule.dao.UserDao;
import com.nagarro.usermodule.entity.Role;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

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
        User userDetails = userDao.findByEmail(jwtTokenUtil.getEmailFromToken(token)).orElse(null);
        String userRole = jwtTokenUtil.getRoleFromToken(token);

        //set the authority to the userRole
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole.toString());

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, List.of(authority)
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
