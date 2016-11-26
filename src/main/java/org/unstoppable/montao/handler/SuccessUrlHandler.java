package org.unstoppable.montao.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Used to redirect authorized user to his profile page after log in.
 */
public class SuccessUrlHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getName();
//            String redirectPath = "/" + username;
//            httpServletResponse.sendRedirect(redirectPath);
//        }
        clearAuthenticationAttributes(httpServletRequest);
    }
}
