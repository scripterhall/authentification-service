package com.authentificationMS.services;

import com.authentificationMS.models.Membre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="membre-service")
public interface MembreFeignClient {

    @GetMapping("/membres/membre")
    Membre getMembreByEmail(@RequestParam("email") String email);

    @PostMapping("/membres/membre")
    Membre ajouterMembre(@RequestBody Membre m);

    @PutMapping("/membres")
    Membre modifierMembre(@RequestBody Membre m);

    
}
