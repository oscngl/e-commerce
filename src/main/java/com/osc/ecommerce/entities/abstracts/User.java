package com.osc.ecommerce.entities.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import com.osc.ecommerce.entities.concretes.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> tokens;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private Collection<Role> roles = new ArrayList<>();

}
