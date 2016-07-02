package org.unstoppable.projectstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unstoppable.projectstack.dao.GenericDAO;
import org.unstoppable.projectstack.dao.GenericDAOImpl;
import org.unstoppable.projectstack.service.UserService;

@Configuration
public class ApplicationContext {
    @Bean
    public GenericDAO genericDAO() {
        return new GenericDAOImpl();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
