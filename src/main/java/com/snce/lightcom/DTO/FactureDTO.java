package com.snce.lightcom.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snce.lightcom.entities.Commande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
public class FactureDTO {
    private Long id;
    private String numero;
    private Date dateFacture;
    private double montanHT;
    private double montantTva;
    private Date dateEcheance;
    private Date dateReglement;
    private Date dateSaisie;
    private Date dateReceptionCompta;
    private Date dateLivraison;
    private boolean receptionSaisie;
    private Commande commande;

    public double getMontantTTC(){
        return montanHT + montantTva;
    }
}
