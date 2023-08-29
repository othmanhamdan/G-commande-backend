package com.example.lightcom.repository;

import com.example.lightcom.entities.Commande;
import com.example.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;


public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c INNER JOIN c.fournisseur f INNER JOIN c.utilisateur u WHERE c.numero LIKE %:numero% AND c.unite LIKE %:unite% AND f.libelle LIKE %:libelle% AND u.nom LIKE %:nom% order by c.id")
    Page<Commande> getAllCommande(@Param("unite") String unite, @Param("numero") String numero,@Param("libelle") String libelle,@Param("nom") String nom, Pageable pageable);

    //Page<Commande> findByNumeroContainingAndUniteContainingAndFournisseur_LibelleContainingAndUtilisateur_NomContainingAndUtilisateur_PrenomContaining(@Param("unite") String unite, @Param("numero") String numero,@Param("libelle") String libelle,@Param("nom") String nom, @Param("prenom") String prenom, Pageable pageable);


    Long countByUniteAndNumeroContainingAndFournisseurIdAndUtilisateurId(@Param("unite") String unite, @Param("numero") String numero,@Param("fournisseur_id") Long fournisseur_id, @Param("utilisateur_id") Long utilisateur_id);


    @Query("select COUNT(c) from Commande c inner join c.utilisateur u where c.id is null and c.supprime=false and c.valide=true and c.cloture = false and c.unite like %:unite% and c.reliquat-c.remise>0.01 and c.dateRelanceFacture < :date and u.id = :utilisateur_id")
    Long countCommandeNonFacturer(@Param("utilisateur_id") Long utilisateur_id, @Param("unite") String unite,@Param("date") Date date);

    @Query("select c from Commande c inner join c.utilisateur u where c.id is null and c.supprime=false and c.valide=true and c.cloture = false and c.unite like :unite and c.reliquat-c.remise>0.01 and c.dateRelanceFacture < :date and u.id = :utilisateur_id order by c.date desc")
    Page<Commande> selectCommandeNonFacturer(@Param("utilisateur_id") Long utilisateur_id, @Param("unite") String unite,@Param("date") Date date, Pageable pageable);

    @Query("select max(c.id) from Commande c where c.date = :year")
    Long getMaxIdPrecedant(@Param("year") int year);




}
