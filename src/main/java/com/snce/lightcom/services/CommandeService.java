package com.snce.lightcom.services;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface CommandeService {
    Commande save(Commande commande);
    double getMontant();
    Commande update(Commande commande);
    void delete(Long id);
    void valide(Commande commande);
    Commande cloture(Commande commande);
    Page<Commande> getAllCommande(String unite,String numero,String libelle,String nom, int page, int size);
    Page<Commande> getAllCommandeNonFacturer(Commande commande, int page, int size);
    Long countCommandeNonFacturer(Commande commande);
    Long countCommande(Commande commande);


    Long countListItemCommande(CommandeItem commandeItem);
    Page<Commande> getAll(int page, int size);

    List<Commande> getAlls();
    Optional<Commande> getCommandeById(Long id);
    Page<Commande> getAllCommandeNonCloturer(String unite,String nom, int page, int size);

}
