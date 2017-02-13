package com.example.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CrayonsUser extends User{

    

    public CrayonsUser(String email, String password,String firstname,String lastname,String role, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.firstname= firstname;
        this.lastname = lastname;
        this.role = role;
        
    }
    
    private String role;
    private String firstname;
    private String lastname;
    
   
    private List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();

    @Override
    public String toString() {
        return String.format(
                "User[username= '%s', password= '%s', firstname = '%s', 'lastname = '%s', 'role= %s']",
                 getUsername(), getPassword(), getFirstname(), getLastname(), getRole());
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


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }

   
   
}
