package com.example.lightcom.services;

import com.example.lightcom.entities.Commande;
import com.example.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CommandeService {
    Commande save(Commande commande);
    double getMontant();
    Commande update(Commande commande);
    void delete(Long id);
    void valide(Commande commande);
    void cloture(Commande commande);
    Page<Commande> getAllCommande(String unite,String numero,String libelle,String nom, int page, int size);
    Page<Commande> getAllCommandeNonFacturer(Commande commande, int page, int size);
    Long countCommandeNonFacturer(Commande commande);
    Long countCommande(Commande commande);

    Page<CommandeItem> listItemCommande(CommandeItem commandeItem, int page, int size);
    Long countListItemCommande(CommandeItem commandeItem);
    Page<Commande> getAll(int page, int size);

    List<Commande> getAlls();
}
