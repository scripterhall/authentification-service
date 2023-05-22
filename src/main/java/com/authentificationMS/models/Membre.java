package com.authentificationMS.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Membre extends Personne{
    
    private Long id ;
    private MembreStatus status ;
}
