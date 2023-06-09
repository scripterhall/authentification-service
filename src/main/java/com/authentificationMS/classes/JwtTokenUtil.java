package com.authentificationMS.classes;

import com.authentificationMS.models.MembreStatus;
import com.authentificationMS.models.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
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
                                MembreStatus status, Date dateInscription,
                                      List<Role> roles, String pwd, byte[] photo) {

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
        claims.put("dateInscription", dateInscription);
        claims.put("status",status);
        claims.put("pwd", pwd);
        claims.put("iat", now);
        claims.put("exp", expiryDate);

        // if (photo != null) {
        //     claims.put("photo", Base64.getEncoder().encodeToString(photo));
        // }

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String generateTokenChefProjet(String email, String username, Long id,
                                      String adresse, String nom,
                                      String prenom, String telephone,
                                      Date dateInscription,String roles, byte[] photo,
                                          String pwd) {

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
        claims.put("dateInscription", dateInscription);
        claims.put("pwd", pwd);
        claims.put("iat", now);
        claims.put("exp", expiryDate);

        // if (photo != null) {
        //     claims.put("photo", Base64.getEncoder().encodeToString(photo));
        // }

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

}
