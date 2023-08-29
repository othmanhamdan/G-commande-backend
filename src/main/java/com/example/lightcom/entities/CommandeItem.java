package com.example.lightcom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referenceArticle;
    private String article;
    private String uniteMesure;
    private int quantite;
    private double prixUnitaire;
    @ManyToOne
    //@JsonIgnore
    private Commande commande;
    @OneToMany
    private List<Facture> factures;

    public double getMontant(){
        return quantite*prixUnitaire;
    }

}
