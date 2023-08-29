package com.example.lightcom.repository;

import com.example.lightcom.entities.Fournisseur;
import com.example.lightcom.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangerRepository extends JpaRepository<Utilisateur, Long> {
}
