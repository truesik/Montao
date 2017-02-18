package org.unstoppable.montao.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
//import org.thymeleaf.ITemplateEngine;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring MVC and Thymeleaf configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.unstoppable.montao.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {
//    private static final String STANDARD_CHARSET = "UTF-8";
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
//        registry.addResourceHandler("/node_modules/**").addResourceLocations("/node_modules");
//        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
//        registry.addResourceHandler("/build/**").addResourceLocations("/build/");
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding(STANDARD_CHARSET);
//        return viewResolver;
//    }
//
//    @Bean
//    public ITemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setEnableSpringELCompiler(true);
//        templateEngine.addDialect(new SpringSecurityDialect());
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//    @Bean
//    public ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
////        templateResolver.setPrefix("/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        // Template cache is true by default. Set to false if you want
//        // templates to be automatically updated when modified.
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
}
