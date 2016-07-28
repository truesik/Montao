package org.unstoppable.projectstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unstoppable.projectstack.dao.*;
import org.unstoppable.projectstack.formatter.UserFormatter;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.UserService;

@Configuration
public class ApplicationContext {
    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public CommunityService communityService() {
        return new CommunityService();
    }

    @Bean
    public ChannelService channelService() {
        return new ChannelService();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOHibernate();
    }

    @Bean
    public CommunityDAO communityDAO() {
        return new CommunityDAOHibernate();
    }

    @Bean
    public ChannelDAO channelDAO() {
        return new ChannelDAOHibernate();
    }

    @Bean
    public UserFormatter userFormatter() {
        return new UserFormatter();
    }
}
