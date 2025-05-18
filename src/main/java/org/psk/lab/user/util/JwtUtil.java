package org.psk.lab.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.psk.lab.user.data.model.MyUser;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET = "somerandomlongsecurestringthatisatleast64byteslongandbase64encoded1234567890";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(MyUser user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("id", user.getUuid())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

}

