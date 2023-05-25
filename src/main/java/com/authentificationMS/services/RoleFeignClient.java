package com.authentificationMS.services;

import com.authentificationMS.models.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="invitation-service")
public interface RoleFeignClient {

    @GetMapping("/roles/membres/{idMembre}")
    List<Role> getRolesByMembreId(@PathVariable("idMembre") Long id);
}
