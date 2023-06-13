package com.selyuto.termbase.filters;

import com.selyuto.termbase.authentication.Authenticator;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.selyuto.termbase.authentication.AuthenticationConstants.SESSION_ID_COOKIE_NAME;

@Component
public class AuthenticationFilter implements Filter {
    private final Authenticator authenticator;

    Map<String, List<String>> publiclyAvailableUris = new HashMap<>();

    public AuthenticationFilter(Authenticator authenticator) {
        this.authenticator = authenticator;
        publiclyAvailableUris.put("/auth/login", Collections.singletonList("GET"));
        publiclyAvailableUris.put("/columns", Collections.singletonList("GET"));
        publiclyAvailableUris.put("/terms", Collections.singletonList("GET"));
        publiclyAvailableUris.put("/suggestions", Collections.singletonList("POST"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        Cookie sessionIdCookie = WebUtils.getCookie(req, SESSION_ID_COOKIE_NAME);
        boolean hasSessionId = false;
        boolean isSessionValid = false;
        if (sessionIdCookie != null) {
            hasSessionId = true;
            isSessionValid = authenticator.getActiveSessions().containsValue(sessionIdCookie.getValue());
        }
        boolean isOptions = req.getMethod().equals("OPTIONS");
        if ((!hasSessionId || !isSessionValid)
                && !isOptions
                && !isPubliclyAvailableUri(req)
                && !isWebSocket(req)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(req, res);
        }
    }

    private boolean isPubliclyAvailableUri(HttpServletRequest req) {
        if (!publiclyAvailableUris.containsKey(req.getRequestURI())) {
            return false;
        }
        return publiclyAvailableUris.get(req.getRequestURI()).contains(req.getMethod());
    }

    private boolean isWebSocket(HttpServletRequest req) {
        return req.getRequestURI().matches("^/tb-websocket.*$");
    }

    @Override
    public void destroy() {

    }
}
