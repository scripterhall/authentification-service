package com.authentificationMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    
    private Long id; //identifiant de projet
    private String nom; //nom de projet
    private String cles; //abvreva de projet
    private Date dateDebut; //date debut de projet
    private Date dateFin;
    private Long chefProjetId;
    private ChefProjet chefProjet;
}
