package com.snce.lightcom.services;

import com.snce.lightcom.entities.Fournisseur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FournisseurService {
    List<Fournisseur> getAllFournisseur();

    Fournisseur saveSupplier(Fournisseur fournisseur);

    Page<Fournisseur> getAllSupplier(String libelle, int page, int size);

    void deleteSupplier(Long id);
}
