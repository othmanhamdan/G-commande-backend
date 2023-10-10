package com.snce.lightcom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String cni;
    @Column(unique = true)
    private String userName;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String e_mail;
    @OneToMany
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Commande> commandes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> appRoles = new ArrayList<>();
}
