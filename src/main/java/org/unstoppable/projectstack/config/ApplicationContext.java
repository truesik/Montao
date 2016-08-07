package org.unstoppable.projectstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.unstoppable.projectstack.dao.*;
import org.unstoppable.projectstack.formatter.UserFormatter;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.MessageService;
import org.unstoppable.projectstack.service.UserService;

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
    public UserFormatter userFormatter() {
        return new UserFormatter();
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
