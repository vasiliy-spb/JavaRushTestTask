package com.game.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MyWebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException { // этот метод запускается при запуске Spring приложения
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "prod");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class}; // указываем в каком классе находится конфигурация (?нашего приложения?)
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class}; // указываем в каком классе находится конфигурация (?сервлета?)
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // эта строка означает, что мы все запросы от пользователя посылаем на сервлет
    }

}