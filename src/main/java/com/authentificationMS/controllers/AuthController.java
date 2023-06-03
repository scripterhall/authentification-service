package com.authentificationMS.controllers;

import com.authentificationMS.classes.JwtTokenUtil;
import com.authentificationMS.models.ChefProjet;
import com.authentificationMS.models.Credentials;
import com.authentificationMS.models.Membre;
import com.authentificationMS.models.Role;
import com.authentificationMS.services.ChefProjetFeignClient;
import com.authentificationMS.services.MembreFeignClient;
import com.authentificationMS.services.RoleFeignClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private String userType = "chefProjet";

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreFeignClient;
    @Autowired
    private RoleFeignClient roleFeignClient;

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
        System.out.println("tests");
        Membre membre = membreFeignClient.getMembreByEmail(email);
        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        if (chefProjet != null && membre == null) {
            byte[] photo = chefProjet.getPhoto();
            if (checkPassword(password, chefProjet.getPwd())) {
                String token = jwtTokenUtil.generateTokenChefProjet(chefProjet.getEmail(), chefProjet.getUsername(), chefProjet.getId(),
                        chefProjet.getAdresse(), chefProjet.getNom(), chefProjet.getPrenom(), chefProjet.getTelephone(),
                        chefProjet.getDateInscription(), "chefProjet", photo, chefProjet.getPwd());

                Map<String, String> response = new HashMap<>();
                response.put("token",token);
                return ResponseEntity.ok().body(response);
            }
        }

        else if (chefProjet == null && membre != null) {
            byte[] photo = membre.getPhoto();
            if (checkPassword(password, membre.getPwd())) {
                String token = jwtTokenUtil.generateTokenMembre(membre.getEmail(), membre.getUsername(), membre.getId(),
                        membre.getAdresse(), membre.getNom(), membre.getPrenom(), membre.getTelephone(),
                        membre.getStatus(), membre.getDateInscription(), roleFeignClient.getRolesByMembreId(membre.getId()),
                        membre.getPwd(), photo);

                Map<String, String> response = new HashMap<>();
                response.put("token",token);
                return ResponseEntity.ok().body(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping("/init")
    public ResponseEntity<Void> prepareSecure(){
        return ResponseEntity.ok().body(null);
    }

}

