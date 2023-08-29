package com.example.lightcom.entities;

import com.example.lightcom.Utils.UtilsDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(unique = true)
    private String numero;
    @Temporal(TemporalType.DATE)
    private Date date;
    private boolean valide;
    private String unite;
    private String observation;
    private int delaiLivraison;
    private int delaiPaiment;
    private String lieuLivraison;
    private boolean supprime;
    private String modePaiment;
    private double reliquat;
    private String referenceDevis;
    @Temporal(TemporalType.DATE)
    private Date dateDerniereFacture;
    private boolean cloture;
    private double remise;
    private boolean countable;
    @Temporal(TemporalType.DATE)
    private Date dateRelanceFacture;
    @Enumerated(EnumType.STRING)
    private TypeCommande typeCommande;
    @ManyToOne
    private Fournisseur fournisseur;
    @OneToMany
    private List<Facture> factures;
    @ManyToOne
    private Utilisateur utilisateur;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commande")
    @JsonBackReference(value = "**")
    private List<CommandeItem> commandeItems = new ArrayList<>();
    public void genererNumero(Long id){
        this.numero =  UtilsDate.getYear(this.date) + "-" + id;
    }

    public void genererNumero(){
        genererNumero(this.id);
    }

    public void facturer(Facture facture){
        facture.calculDateEcheance();
        this.reliquat -=facture.getMontanHT();
        this.dateDerniereFacture = UtilsDate.max(dateDerniereFacture, facture.getDateFacture());
    }



}
