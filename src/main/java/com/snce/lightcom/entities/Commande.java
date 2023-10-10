package com.snce.lightcom.entities;

import com.snce.lightcom.Utils.UtilsDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(mappedBy = "commande")
    private List<Facture> factures;

    @ManyToOne()
    private AppUser appUser;
    @OneToMany(mappedBy = "commande")
    private List<CommandeItem> commandeItems ;
    public void genererNumero(Long id){
        this.numero =  UtilsDate.getYear(this.date) + "-" + id;
    }

    public void genererNumero(){
        genererNumero(this.id);
    }

   /* public void facturer(Facture facture){
        facture.calculDateEcheance();
        this.reliquat -=facture.getMontanHT();
        this.dateDerniereFacture = UtilsDate.max(dateDerniereFacture, facture.getDateFacture());
    }*/





}
