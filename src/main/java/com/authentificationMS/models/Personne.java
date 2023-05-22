package com.authentificationMS.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class Personne {
    private String nom ;//nom d'un personne
    private String prenom; //prenom d'un personne
    private String adresse ;//adresse d'un personne
    private String telephone ; //telephone d'un personne
    private String email ;//email d'un personne
    private String pwd ;//password d'un personne
    private Date  dateInscription ;//date inscription d'un personne
    private String username;//le nom d'utilisateur 
}
