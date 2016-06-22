package org.unstoppable.projectstack.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Automatically register the springSecurityFilterChain Filter for every URL in your application
 * Add a ContextLoaderListener that loads the SecurityConfig.
 */
public class SpringSecurityInit extends AbstractSecurityWebApplicationInitializer {
}
