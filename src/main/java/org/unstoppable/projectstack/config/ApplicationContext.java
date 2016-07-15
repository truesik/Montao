package org.unstoppable.projectstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unstoppable.projectstack.dao.*;
import org.unstoppable.projectstack.service.CommunitySevice;
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

    @Bean
    public CommunitySevice communitySevice() {
        return new CommunitySevice();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOHibernate();
    }

    @Bean
    public CommunityDAO communityDAO() {
        return new CommunityDAOHibernate();
    }
}
