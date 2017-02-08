package com.example.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CrayonsUser extends User{

    

    public CrayonsUser(String email, String password,String firstname,String lastname, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.firstname= firstname;
        this.lastname = lastname;
        
    }
    private String firstname;
    private String lastname;
    
   
    private List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();

    @Override
    public String toString() {
        return String.format(
                "User[username= '%s', password= '%s', firstname = '%s', 'lastname = '%s']",
                 getUsername(), getPassword(), getFirstname(), getLastname());
    }


    public List<GrantedAuthority> getAuth() {
        return auth;
    }
    public void setAuth(List<GrantedAuthority> auth) {
        this.auth = auth;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

   
   
}
