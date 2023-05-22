package com.authentificationMS.classes;

import com.authentificationMS.models.MembreStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateTokenMembre(String email, String username, Long id,
                                String adresse, String nom,
                                String prenom, String telephone,
                                MembreStatus status, Date date_inscription) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("username", username);
        claims.put("id", id);
        claims.put("adresse",adresse);
        claims.put("nom",nom);
        claims.put("prenom",prenom);
        claims.put("telephone",telephone);
        claims.put("iat", now);
        claims.put("exp", expiryDate);

        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public String generateTokenChefProjet(String email, String username, Long id,
                                      String adresse, String nom,
                                      String prenom, String telephone,
                                      Date date_inscription
                                      ) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("username", username);
        claims.put("id", id);
        claims.put("adresse",adresse);
        claims.put("nom",nom);
        claims.put("prenom",prenom);
        claims.put("telephone",telephone);
        claims.put("iat", now);
        claims.put("exp", expiryDate);

        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

}
