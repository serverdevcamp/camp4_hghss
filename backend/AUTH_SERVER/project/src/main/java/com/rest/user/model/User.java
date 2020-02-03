package com.rest.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {
    private int id;//idx
//    private int nickname_id;
    private String email;
    private String hashedPassword;
    private String salt;
    private String password;
    private String grade;



    private String string;//EDIT!!



    public String getString() {
        return this.hashedPassword + " , " + this.salt + " , " + this.email;
    }
}

