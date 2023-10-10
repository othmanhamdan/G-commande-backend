package com.snce.lightcom.repository;

import com.snce.lightcom.entities.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Page<Fournisseur> findByLibelleContainsOrderByIdDesc(String libelle , Pageable pageable);
}
