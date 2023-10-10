package com.snce.lightcom.DTO;

import com.snce.lightcom.Utils.UtilsDate;
import com.snce.lightcom.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
public class CommandeDTO {
    private Long id;
    private String numero;
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
    private Date dateDerniereFacture;
    private boolean cloture;
    private double remise;
    private boolean countable;
    private Date dateRelanceFacture;
    private TypeCommande typeCommande;
    private Fournisseur fournisseur;
    private AppUser appUser;
    /*public void genererNumero(Long id){
     //   this.numero =  UtilsDate.getYear(this.date) + "-" + id;
    //}
    //public void genererNumero(){
     //   genererNumero(this.id);
    }*/







}
