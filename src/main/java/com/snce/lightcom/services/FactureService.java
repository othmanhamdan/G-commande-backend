package com.snce.lightcom.services;

import com.snce.lightcom.DTO.FactureDTO;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.Facture;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface FactureService {
    Facture save(Facture facture);
    void recueParCompta(Facture facture);
    void reglee(Facture facture);
    //list()
    List<Facture> getByCommande(Commande commande);

    Long countByListEchue(Facture facture, double montantMin, double montantMax);

    Page<FactureDTO> listReglee(String libelle, String userName, int page, int size);

    Long countListReglee(Facture facture);

    Page<FactureDTO> listImport(Date date_debut,Date date_fin, boolean recupEnStock,boolean recupEncpt,String libelle,String numero, int page,int size);

    Long countListImport(Facture facture, boolean recuEnCpt, Date date_debut, Date date_fin);
    List<Facture> getAllFactureByCommande(Long id);

    Page<FactureDTO> getAllCommandeServise(String numero, String libelle, String userName, int page, int size);

    Facture confirmerReception(Facture facture);
}
