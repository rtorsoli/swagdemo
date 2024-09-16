package com.example.wallet.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wallet.model.persistence.TenantPersistent;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class JWTUtil {
    
    private static final String ROLE = "role";
    private static final String ID = "id";
    
    @Autowired
    private Supplier<OffsetDateTime> offsetDateTimeGet;
    
    @Value("${jwt.expiration}")
    private String expirationTimeInSeconds;
    
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Jwts.SIG.HS256.key().build(); 
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(offsetDateTimeGet.get().toInstant()));
    }

    public TokenInfo generateToken(TenantPersistent tenant) {
        var claims = new HashMap<String, Object>() {{
                put(ROLE, tenant.getRoles());
                put(ID, tenant.getRoles());
        }};
        var expirationDate = offsetDateTimeGet.get().plusSeconds(Long.parseLong(expirationTimeInSeconds));

        return createToken(expirationDate, claims, tenant.getNickname(), tenant.getEmail());
    }

    private TokenInfo createToken(OffsetDateTime expirationDate, Map<String, Object> claims, String issuer, String subject) {
       
        var createdAt = offsetDateTimeGet.get();
        var token = Jwts.builder()
                .claims(claims)
                .issuer(issuer)
                .subject(subject)
                .issuedAt(Date.from(createdAt.toInstant()))
                .id(UUID.randomUUID().toString())
                .expiration(Date.from(expirationDate.toInstant()))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return TokenInfo.builder()
                .token(token)
                .issuedAt(Date.from(createdAt.toInstant()))
                .expiresAt(Date.from(expirationDate.toInstant()))
                .build();        
    }

    public Boolean validateToken(String token, String nickName, String email) {
        final String extractedUsername = extractUsername(token);
        return ((extractedUsername.equals(email) || extractedUsername.equals(email)) && !isTokenExpired(token));
    }
}