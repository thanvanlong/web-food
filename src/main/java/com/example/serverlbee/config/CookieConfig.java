package com.example.serverlbee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@Configuration
public class CookieConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setHttpOnly(true);
        servletContext.getSessionCookieConfig().setPath("/api");
        servletContext.getSessionCookieConfig().setSecure(true);
        servletContext.getSessionCookieConfig().setMaxAge(86400);
    }
}
