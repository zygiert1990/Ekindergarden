package ekindergarten.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;

@Component
class JwtValidator {
    private static final String SECRET = "TECZA1234";

    public JwtUser validate(String token) {
        try {
            Claims body = Jwts
                    .parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            JwtUser jwtUser = new JwtUser();
            jwtUser.setUserName(body.getSubject());
            jwtUser.setId(Long.parseLong((String) body.get("userId")));
            jwtUser.setRole((String) body.get("role"));

            return jwtUser;
        } catch (MalformedJwtException e) {
            return null;
        }
    }
}
