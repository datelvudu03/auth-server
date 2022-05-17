package com.authserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "contact")
public class Contact {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email,street,city,zip,province;
}
