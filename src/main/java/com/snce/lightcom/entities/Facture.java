package com.snce.lightcom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snce.lightcom.Utils.UtilsDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Commande commande;

    public double getMontantTTC(){
        return montanHT + montantTva;
    }
}
