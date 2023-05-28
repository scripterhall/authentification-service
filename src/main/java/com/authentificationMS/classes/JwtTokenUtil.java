package com.authentificationMS.classes;

import com.authentificationMS.models.MembreStatus;
import com.authentificationMS.models.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
                                MembreStatus status, Date date_inscription,
                                      List<Role> roles) {

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
        claims.put("roles",roles);
        claims.put("iat", now);
        claims.put("exp", expiryDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String generateTokenChefProjet(String email, String username, Long id,
                                      String adresse, String nom,
                                      String prenom, String telephone,
                                      Date date_inscription,String roles
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
        claims.put("roles",roles);
        claims.put("iat", now);
        claims.put("exp", expiryDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

}
