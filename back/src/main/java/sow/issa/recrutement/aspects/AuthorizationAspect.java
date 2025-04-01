package sow.issa.recrutement.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import sow.issa.recrutement.exceptions.UserNotAllowedException;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.security.SecurityUtils;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationAspect {
    private final UserRepository userRepository;
    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ACCES_DENIED = "Accès refusé pour l'utilisateur %s";

    @Around("@annotation(sow.issa.recrutement.annotation.Authorization)")
    public Object checkAdminPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException("user not found in the database"));

        if(!StringUtils.equals(ADMIN_EMAIL, user.getEmail())) {
            throw new UserNotAllowedException(String.format(ACCES_DENIED, user.getEmail()));
        }

        return joinPoint.proceed();
    }
}
