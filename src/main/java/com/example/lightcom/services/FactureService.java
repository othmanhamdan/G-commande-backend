package com.example.lightcom.services;

import com.example.lightcom.entities.*;

import java.util.Date;
import java.util.List;

public interface FactureService {
    Facture save(Facture facture);
    void recueParCompta(Facture facture);
    void reglee(Facture facture);
    //list()
    List<Facture> getByCommande(Commande commande);

    Facture saveFactureDeCommande(Facture facture, List<Commande> commandeGroupe);

    List<Facture> listEchue(Facture facture, double montantMin, double montantMax, int page, int size);
    Long countByListEchue(Facture facture, double montantMin, double montantMax);
    List<Facture> listReglee(Facture facture, int page, int size);
    Long countListReglee(Facture facture);
    //List<Facture> listImport(Facture facture, boolean recuEnCpt);

    List<Facture> listImport(Facture facture, boolean recuEnCpt, Date date_debut, Date date_fin, int page, int size);

    Long countListImport(Facture facture, boolean recuEnCpt, Date date_debut, Date date_fin);
}
