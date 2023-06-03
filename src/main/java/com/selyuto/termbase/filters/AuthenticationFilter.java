package com.selyuto.termbase.filters;

import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class AuthenticationFilter implements Filter {
    private final Authenticator authenticator;

    public AuthenticationFilter(Authenticator authenticator, UserService userService) {
        this.authenticator = authenticator;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        Cookie sessionIdCookie = WebUtils.getCookie(req, "sessionIdTm");
        boolean hasSessionId = false;
        boolean isSessionValid = false;
        if (sessionIdCookie != null) {
            hasSessionId = true;
            isSessionValid = authenticator.getActiveSessions().containsValue(sessionIdCookie.getValue());
        }
        boolean isOptions = req.getMethod().equals("OPTIONS");
        if ((!hasSessionId || !isSessionValid) && !req.getRequestURI().equals("/auth/login") && !isOptions) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
