package com.authserver.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact extends RepresentationModel<Contact> {
    @Id @Column(name = "appUser_id")
    private Long id;
    private String email,street,city,zip;
    @OneToOne @MapsId @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    public Contact() {
    }

    public Contact(String email, String street, String city, String zip, AppUser appUser) {
        this.email = email;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", appUser=" + appUser +
                '}';
    }
}
