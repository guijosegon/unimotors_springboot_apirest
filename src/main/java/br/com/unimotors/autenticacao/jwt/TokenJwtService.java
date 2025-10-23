package br.com.unimotors.autenticacao.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class TokenJwtService {

    @Value("${app.jwt.segredo}")
    private String segredo;

    @Value("${app.jwt.expMinutos:60}")
    private long expMinutos;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(String subject, List<String> papeis) {
        var agora = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", papeis)
                .setIssuedAt(Date.from(agora))
                .setExpiration(Date.from(agora.plusSeconds(expMinutos * 60)))
                .signWith(key)
                .compact();
    }

    public Claims validar(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
