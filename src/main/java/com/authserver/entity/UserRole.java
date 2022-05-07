package com.authserver.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user-role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    @OneToMany(mappedBy = "appUser")
    private Set<AppUser> appUserSet;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public UserRole(Long id, String role, Set<AppUser> appUserSet) {
        this.id = id;
        this.role = role;
        this.appUserSet = appUserSet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<AppUser> getAppUserSet() {
        return appUserSet;
    }

    public void setAppUserSet(Set<AppUser> appUserSet) {
        this.appUserSet = appUserSet;
    }

}
