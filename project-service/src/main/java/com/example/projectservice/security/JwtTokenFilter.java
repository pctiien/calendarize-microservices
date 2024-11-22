package com.example.projectservice.security;

import com.example.projectservice.entity.ProjectUser;
import com.example.projectservice.repository.ProjectUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ProjectUserRepository projectUserRepository ;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider ;

    public static ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        try{
            if(token!=null && jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                List<ProjectUser> projectUserList = projectUserRepository.findByUserId(userId);
                List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
                projectUserList.forEach(pU->{
                    pU.getProjectRoles().forEach(role->{
                        authorities.add(new SimpleGrantedAuthority(
                                String.format("PROJECT_%s_%s",pU.getProject().getId().toString(),role.getName().toString())
                        ));
                    });
                });
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),null,authorities
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                tokenHolder.set(token);
                filterChain.doFilter(request,response);
            }else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid or expired token");
            }
        }catch (ExpiredJwtException e)
        {
            logger.error("Token has expired for request {}",request.getRequestURI());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token has expired for request");
        }catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token");

        }
    }

    private String extractToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if(authHeader !=null && authHeader.startsWith("Bearer"))
        {
            return authHeader.replace("Bearer ","");
        }
        return null;
    }

}
