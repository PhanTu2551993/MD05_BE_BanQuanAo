package ra.project_md05.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ra.project_md05.security.principal.UserDetailCustom;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt_secret}")
    private String SECRET_KEY;
    @Value("${jwt_expiration}")
    private Long EXPIRED_ACCESS;

    public String generateToken(UserDetailCustom userDetailCustom) {
        return Jwts.builder()
                .setSubject(userDetailCustom.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED_ACCESS))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException ex) {
            log.error("Invalid JWT token {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("Signature exception {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Malformed URL exception {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty {}", ex.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
