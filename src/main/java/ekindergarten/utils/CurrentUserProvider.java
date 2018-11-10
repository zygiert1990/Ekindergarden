package ekindergarten.utils;

import ekindergarten.security.jwt.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public final class CurrentUserProvider {

    public static long provideUserId() {
        return ((JwtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static String provideUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
