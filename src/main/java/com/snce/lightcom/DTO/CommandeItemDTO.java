package com.snce.lightcom.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snce.lightcom.entities.Commande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
public class CommandeItemDTO {
    private Long id;
    private String referenceArticle;
    private String article;
    private String uniteMesure;
    private int quantite;
    private double prixUnitaire;
    private Commande commande;
}
