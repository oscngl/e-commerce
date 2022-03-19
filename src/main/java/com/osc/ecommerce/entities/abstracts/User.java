package com.osc.ecommerce.entities.abstracts;

import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "confirmed")
    private boolean confirmed = false;

    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> tokens;

}
