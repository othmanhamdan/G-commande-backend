package com.example.lightcom.entities;

import com.example.lightcom.Utils.UtilsDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @Temporal(TemporalType.DATE)
    private Date dateFacture;
    private double montanHT;
    private double montantTva;
    @Temporal(TemporalType.DATE)
    private Date dateEcheance;
    @Temporal(TemporalType.DATE)
    private Date dateReglement;
    @Temporal(TemporalType.DATE)
    private Date dateSaisie;
    @Temporal(TemporalType.DATE)
    private Date dateReceptionCompta;
    @Temporal(TemporalType.DATE)
    private Date dateLivraison;
    private boolean receptionSaisie;
    @ManyToOne
    private Commande commande;

    public void calculDateEcheance(){
        this.dateEcheance = UtilsDate.incDateByDays(this.dateLivraison, this.commande.getDelaiPaiment());
    }
}
