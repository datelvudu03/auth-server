package com.authserver.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "appUsers")
public class AppUser extends RepresentationModel<AppUser> {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
    private Long id;
    private String username;
    private String password;
    private String role;

    @OneToOne(mappedBy = "appUser",cascade = CascadeType.ALL) @PrimaryKeyJoinColumn
    private Contact contact;

    public AppUser() {
    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
