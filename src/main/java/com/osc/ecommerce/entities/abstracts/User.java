package com.osc.ecommerce.entities.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
public abstract class User implements UserDetails {

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

    private String role;

    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> tokens;

}
