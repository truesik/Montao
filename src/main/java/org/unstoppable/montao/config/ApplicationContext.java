package org.unstoppable.montao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.unstoppable.montao.dao.*;
import org.unstoppable.montao.service.*;

import java.util.Properties;

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
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean
    public SubscriptionService subscriptionService() {
        return new SubscriptionService();
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
    public MessageDAO messageDAO() {
        return new MessageDAOHibernate();
    }

    @Bean
    public SubscriptionDAO subscriptionDAO() {
        return new SubscriptionDAOHibernate();
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("test@gmail.com");
        mailSender.setPassword("test");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.debug", true);

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
