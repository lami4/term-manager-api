package com.selyuto.termbase.interceptors;

import com.selyuto.termbase.annotations.RequiredPrivileges;
import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.enums.Privilege;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.selyuto.termbase.authentication.AuthenticationConstants.SESSION_ID_COOKIE_NAME;

@Component
public class PrivilegeCheckInterceptor implements HandlerInterceptor {

    private final Authenticator authenticator;

    public PrivilegeCheckInterceptor(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RequiredPrivileges annotation = method.getMethod().getAnnotation(RequiredPrivileges.class);
            if (annotation == null) {
                return true;
            }
            Cookie sessionIdCookie = WebUtils.getCookie(request, SESSION_ID_COOKIE_NAME);
            if (sessionIdCookie == null) {
                return true;
            }
            Privilege[] requiredPrivileges = annotation.privileges();
            List<Long> userPrivileges = authenticator.getSessionPrivileges(sessionIdCookie.getValue());
            for (Privilege privilege : requiredPrivileges) {
                if (userPrivileges.contains(privilege.id)) {
                    return true;
                }
            }
            request.getRequestDispatcher("/unauthorized").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
