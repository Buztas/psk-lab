package org.psk.lab.util.interceptor;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "interceptor.loginvocations.enabled", havingValue = "true", matchIfMissing = true)
public class LogInvocationsInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInvocationsInterceptor.class.getName());

    @Pointcut("@within(org.psk.lab.util.interceptor.LogInvocations) || @annotation(org.psk.lab.util.interceptor.LogInvocations)")
    public void loggedMethods(){}

    @Before("loggedMethods()")
    public void logExecution(JoinPoint jp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null ? auth.getName() : "anonymous");
        String roles = (auth != null ? auth.getAuthorities().toString() : "[]");
        String method = jp.getSignature().toLongString();
        LocalDateTime start = LocalDateTime.now();

        logger.info("[INTERCEPTING] user={} roles={} time={} method={}", username, roles, start, method);
    }
}
