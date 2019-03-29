package com.ejtrio.springdatarest.infrastructure.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "SDR", name="USER")
@Data
public class User extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "user")
    private Collection<Order> orders;
}
