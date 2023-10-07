package com.geetodolist.todolistapps.security;

import com.geetodolist.todolistapps.util.JWTTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String tokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            jwtToken = tokenHeader.substring(7);

            try {
                username = jwtTokenUtil.getUserNameFromToken(jwtToken);
            }catch (IllegalArgumentException ex){
                throw new RuntimeException("Unable to get JWT token");
            }catch (ExpiredJwtException ex){
                throw new RuntimeException("Jwt token has Expired");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsServices.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
