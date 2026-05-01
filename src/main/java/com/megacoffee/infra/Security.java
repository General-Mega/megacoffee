package com.megacoffee.infra;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.megacoffee.modules.user.UserVO;
import com.megacoffee.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

final public class Security {
    private static final Logger logger = LoggerFactory.getLogger(Security.class);

    static public Long idx() {
        final UserVO user = user();
        if (user != null) {
            return user.getSeq();
        }

        final CustomUserDetails details = currentUserDetails();
        return details != null ? details.getSeq() : null;
    }

    static public UserVO user() {
        UserVO sessionUser = currentSessionUser();
        if (sessionUser != null) {
            return sessionUser;
        }

        Authentication authentication = currentAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserVO) {
            return (UserVO) principal;
        }

        return null;
    }

    static public CustomUserDetails currentUserDetails() {
        Authentication authentication = currentAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }

        return null;
    }

    static public Object principal() {
        Authentication authentication = currentAuthentication();
        if (authentication == null) {
            return null;
        }

        return authentication.getPrincipal();
    }

    static public Authentication login(
            final String username,
            final Object password,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        return login(username, password, null, authenticationManager);
    }

    static public Authentication login(
            final String username,
            final Object password,
            final Object details,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        if (username == null) {
            throw new IllegalArgumentException("username may not be null.");
        }
        if (authenticationManager == null) {
            throw new IllegalArgumentException("authenticationManager may not be null.");
        }

        UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
        request.setDetails(details);
        return login(request, authenticationManager);
    }

    static public Authentication login(
            final Authentication request,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        if (request == null) {
            throw new IllegalArgumentException("request may not be null.");
        }
        if (authenticationManager == null) {
            throw new IllegalArgumentException("authenticationManager may not be null.");
        }

        final Authentication authentication = authenticationManager.authenticate(request);
        if (authentication == null) {
            logout();
            return null;
        }

        if (!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication must be authenticated.");
        }

        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException("SecurityContext is not available.");
        }

        context.setAuthentication(authentication);
        if (logger.isInfoEnabled()) {
            logger.info("User({}) was logged in.", authentication.getName());
        }

        return authentication;
    }

    static public void logout() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            SecurityContextHolder.clearContext();
            return;
        }

        final Object details = authentication.getDetails();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();

        if (logger.isInfoEnabled()) {
            logger.info("User({}) was logged out. ({})", authentication.getName(), detailsToString(details));
        }
    }

    static private Authentication currentAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    static private UserVO currentSessionUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        if (request == null) {
            return null;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        Object value = session.getAttribute("user");
        if (value instanceof UserVO) {
            return (UserVO) value;
        }

        return null;
    }

    /**
     * For Authentication#getDetails().
     *
     * @param details
     * @return
     */
    static private String detailsToString(final Object details) {
        if (WebAuthenticationDetails.class.isInstance(details)) {
            return ((WebAuthenticationDetails) details).getRemoteAddress();
        }

        return Objects.toString(details, "null");
    }

    private Security() {
    }
}
