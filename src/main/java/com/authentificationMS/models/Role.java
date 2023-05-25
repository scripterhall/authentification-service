package com.authentificationMS.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Role {
    
    @EmbeddedId
    private RolePK pk;
    private String type;
    private String permission ; 
    private String description ;

    
    @Enumerated(EnumType.STRING) 
    private RoleStatus status;

    @Transient
    private Projet projet ;

    @Transient
    private Membre membre ;
}
