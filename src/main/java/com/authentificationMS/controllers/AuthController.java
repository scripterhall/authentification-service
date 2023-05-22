package com.authentificationMS.controllers;

import com.authentificationMS.classes.JwtTokenUtil;
import com.authentificationMS.models.ChefProjet;
import com.authentificationMS.models.Credentials;
import com.authentificationMS.models.Membre;
import com.authentificationMS.services.ChefProjetFeignClient;
import com.authentificationMS.services.MembreFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private String userType;

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreFeignClient;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private boolean checkPassword(String password, String encryptedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, encryptedPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        Membre membre = membreFeignClient.getMembreByEmail(email);
        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        if (membre != null && chefProjet != null && checkPassword(password, membre.getPwd()) && checkPassword(password, chefProjet.getPwd())) {
            Map<String, String> response = new HashMap<>();
            response.put("userType", "chooseType");
            return ResponseEntity.ok().body(response);
        } else if (chefProjet != null && membre == null) {
            if (checkPassword(password, chefProjet.getPwd())) {
                String token = jwtTokenUtil.generateTokenChefProjet(chefProjet.getEmail(), chefProjet.getUsername(), chefProjet.getId(),
                        chefProjet.getAdresse(), chefProjet.getNom(), chefProjet.getPrenom(), chefProjet.getTelephone(),
                        chefProjet.getDateInscription());

                Map<String, String> response = new HashMap<>();
                response.put("userType", "chefProjet");

                response.put("token",token);
                return ResponseEntity.ok().body(response);
            }
        } else if (chefProjet == null && membre != null) {
            if (checkPassword(password, membre.getPwd())) {
                String token = jwtTokenUtil.generateTokenMembre(membre.getEmail(), membre.getUsername(), membre.getId(),
                        membre.getAdresse(), membre.getNom(), membre.getPrenom(), membre.getTelephone(),
                        membre.getStatus(), membre.getDateInscription());

                Map<String, String> response = new HashMap<>();
                response.put("userType", "membre");

                response.put("token",token);
                return ResponseEntity.ok().body(response);
            }
        } else if (membre != null && chefProjet != null && !checkPassword(password, membre.getPwd()) || !checkPassword(password, chefProjet.getPwd())){

            Map<String, String> response = new HashMap<>();

            if (checkPassword(password, membre.getPwd())) {
                String token = jwtTokenUtil.generateTokenMembre(membre.getEmail(), membre.getUsername(), membre.getId(),
                        membre.getAdresse(), membre.getNom(), membre.getPrenom(), membre.getTelephone(),
                        membre.getStatus(), membre.getDateInscription());

                response.put("userType", "membre");
                response.put("token",token);

                return ResponseEntity.ok().body(response);

            } else if (checkPassword(password, chefProjet.getPwd())) {
                String token = jwtTokenUtil.generateTokenChefProjet(chefProjet.getEmail(), chefProjet.getUsername(), chefProjet.getId(),
                        chefProjet.getAdresse(), chefProjet.getNom(), chefProjet.getPrenom(), chefProjet.getTelephone(),
                        chefProjet.getDateInscription());

                response.put("userType", "chefProjet");
                response.put("token",token);

                return ResponseEntity.ok().body(response);

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/chefProjet")
    public ResponseEntity<Map<String, String>> authenticateChefProjet(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        if (chefProjet != null && checkPassword(password, chefProjet.getPwd())) {
            String token = jwtTokenUtil.generateTokenChefProjet(chefProjet.getEmail(), chefProjet.getUsername(), chefProjet.getId(),
                    chefProjet.getAdresse(), chefProjet.getNom(), chefProjet.getPrenom(), chefProjet.getTelephone(),
                    chefProjet.getDateInscription());

            Map<String, String> response = new HashMap<>();
            response.put("userType", "chefProjet");
            response.put("token",token);

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @PostMapping("/membre")
    public ResponseEntity<Map<String, String>> authenticateMembre(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        Membre membre = membreFeignClient.getMembreByEmail(email);

        if (membre != null && checkPassword(password, membre.getPwd())) {
            String token = jwtTokenUtil.generateTokenMembre(membre.getEmail(), membre.getUsername(), membre.getId(),
                    membre.getAdresse(), membre.getNom(), membre.getPrenom(), membre.getTelephone(),
                    membre.getStatus(), membre.getDateInscription());

            Map<String, String> response = new HashMap<>();
            response.put("userType", "membre");
            response.put("token",token);

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

