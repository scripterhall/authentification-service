package com.authentificationMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credentials {
    private String email;
    private String pwd;

    // Ajoutez des constructeurs, des getters et des setters

    // Exemple :
    // public Credentials(String username, String password) {
    //     this.username = username;
    //     this.password = password;
    // }

    // public String getUsername() {
    //     return username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }
}
