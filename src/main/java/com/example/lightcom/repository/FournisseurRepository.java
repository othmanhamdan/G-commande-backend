package com.example.lightcom.repository;

import com.example.lightcom.entities.Facture;
import com.example.lightcom.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
