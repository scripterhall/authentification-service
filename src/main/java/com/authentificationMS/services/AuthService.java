package com.authentificationMS.services;

import com.authentificationMS.models.ChefProjet;
import com.authentificationMS.models.Membre;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

@Service
public class AuthService {
    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreFeignClient;

    public String authenticate(String email, String password) {
        Membre membre = membreFeignClient.getMembreByEmail(email);
        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        // Vérifier si l'utilisateur est à la fois un membre et un chef de projet
        if (membre != null && chefProjet != null) {
            // Si l'utilisateur est à la fois un membre et un chef de projet, vous pouvez choisir de renvoyer un token pour l'un d'eux.
            // Ici, nous renvoyons le token pour le membre, mais vous pouvez modifier cela selon vos besoins.
            String token = generateToken(membre);
            return token+" deux !";
        }

        // Authentifier l'utilisateur en tant que membre si l'objet membre existe et le mot de passe correspond
        if (membre != null && checkPassword(password, membre.getPwd())) {
            String token = generateToken(membre);
            return token;
        }

        // Authentifier l'utilisateur en tant que chef de projet si l'objet chefProjet existe et le mot de passe correspond
        if (chefProjet != null && checkPassword(password, chefProjet.getPwd())) {
            String token = generateToken(chefProjet);
            return token;
        }

        // Retourner un message d'erreur si le mot de passe est incorrect
        if (membre != null || chefProjet != null) {
            return "Mot de passe incorrect";
        }

        // Retourner un message d'erreur si l'email n'existe pas
        return "Email inexistant";
    }

    private boolean checkPassword(String password, String encryptedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, encryptedPassword);
    }

    private String generateToken(Membre membre) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Génération du token JWT en utilisant la clé sécurisée
        String token = Jwts.builder()
                .setSubject(membre.getEmail())
                // Ajoutez d'autres claims ou informations que vous souhaitez inclure dans le token
                .signWith(secretKey)
                .compact();

        return token;
    }

    private String generateToken(ChefProjet chefProjet) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Génération du token JWT en utilisant la clé sécurisée
        String token = Jwts.builder()
                .setSubject(chefProjet.getEmail())
                // Ajoutez d'autres claims ou informations que vous souhaitez inclure dans le token
                .signWith(secretKey)
                .compact();

        return token;
    }
}
