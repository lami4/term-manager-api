package com.selyuto.termbase.configs;

import com.selyuto.termbase.interceptors.PrivilegeCheckInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    private final PrivilegeCheckInterceptor privilegeCheckInterceptor;

    public InterceptorsConfig(PrivilegeCheckInterceptor privilegeCheckInterceptor) {
        this.privilegeCheckInterceptor = privilegeCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(privilegeCheckInterceptor);
    }
}