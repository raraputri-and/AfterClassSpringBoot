package com.metrodataacademy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${auth.app.jwtExpirationMs}")
    private Long refreshTokenDurationMs;
    @Value("${auth.app.jwtRefreshExpirationMs}")
    private Long accessTokenDurationMs;

    @Value("${auth.app.jwtSecret}")
    private String accessTokenSecret;
    @Value("${auth.app.jwtRefreshSecret}")
    private String refreshTokenSecret;

    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void init() {
        this.accessKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.getOrDefault("roles", new ArrayList<>());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(String refreshToken, String username) {
        return createToken(refreshToken, username);
    }

    public String generateToken(Map<String, Object> claims, String username) {
        return createToken(claims, username);
    }

    public String generateRefreshToken(Map<String, Object> claims, String username) {
        return createRefreshToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(accessKey, SignatureAlgorithm.HS256).compact();
    }

    private String createToken(String refreshToken, String subject) {
        Claims claims = Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(refreshToken).getBody();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenDurationMs)) //60 minute
                .signWith(accessKey, SignatureAlgorithm.HS256).compact();
    }

    public String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenDurationMs))
                .signWith(refreshKey, SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token) {
        try {
            String cleanToken = token.replace("Bearer ", "");
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(cleanToken);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public Boolean validateRefreshToken(String token) {
        try {
            String cleanToken = token.replace("Bearer ", "");
            Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(cleanToken);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Boolean isRefreshTokenExpired(String token) {
        return extractExpirationDateRefresh(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }
    private Date extractExpirationDateRefresh(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }
}