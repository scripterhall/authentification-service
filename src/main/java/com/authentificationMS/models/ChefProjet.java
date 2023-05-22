package com.authentificationMS.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ChefProjet extends Personne{

    private Long id;//identifiant de chef de projet 
    private boolean competanceAnalyseDonnees;//confirmation de niveau sur quelle point
    private boolean competanceDeGestion;//confirmation de niveau
    private byte[] photo;
}
