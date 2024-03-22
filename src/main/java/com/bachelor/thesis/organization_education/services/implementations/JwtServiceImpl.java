package com.bachelor.thesis.organization_education.services.implementations;

import java.util.Map;
import java.util.Date;
import lombok.NonNull;
import java.util.HashMap;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import java.util.function.Function;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.services.interfaces.JwtService;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey;
    private final long jwtExpiration;
    private final long refreshExpiration;

    @Autowired
    public JwtServiceImpl(Dotenv dotenv) {
        this.secretKey = dotenv.get("SECURITY_KEY");
        this.jwtExpiration = Long.parseLong(dotenv.get("EXPIRATION"));
        this.refreshExpiration = Long.parseLong(dotenv.get("REFRESH_TOKEN_EXPIRATION"));
    }

    @Override
    public String extractUsername(String token) throws JwtException, IllegalArgumentException {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) throws JwtException, IllegalArgumentException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) throws NullPointerException {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) throws NullPointerException {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) throws NullPointerException {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            @NonNull UserDetails userDetails,
            long expiration
    ) throws NullPointerException {
        return Jwts
                .builder()
                .claims().add(extraClaims).and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, @NonNull UserDetails userDetails) throws JwtException, IllegalArgumentException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenActive(token);
    }

    @Override
    public boolean isTokenActive(String accessToken) throws JwtException, IllegalArgumentException {
        return !extractExpiration(accessToken).before(new Date());
    }

    private Date extractExpiration(String token) throws JwtException, IllegalArgumentException {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws JwtException, IllegalArgumentException {
        var jwtParser = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build();

        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    private @NonNull SecretKey getSecretKey() {
        return Keys
                .hmacShaKeyFor(
                        secretKey.getBytes(StandardCharsets.UTF_8)
                );
    }
}
