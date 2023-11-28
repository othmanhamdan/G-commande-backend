package com.snce.lightcom.repository;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.proj.CommandeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c INNER JOIN c.fournisseur f INNER JOIN c.appUser u WHERE c.numero LIKE %:numero% AND c.unite LIKE %:unite% AND f.libelle LIKE %:libelle% AND u.nom LIKE %:nom% order by c.id DESC ")
    Page<Commande> getAllCommande(@Param("unite") String unite, @Param("numero") String numero,@Param("libelle") String libelle,@Param("nom") String nom, Pageable pageable);

    @Query("SELECT c FROM Commande c INNER JOIN c.appUser u WHERE c.unite LIKE %:unite% AND c.cloture = false and u.nom LIKE %:nom% order by c.id DESC ")
    Page<Commande> getAllCommandeNonCloturer(@Param("unite") String unite,@Param("nom") String nom, Pageable pageable);


    Long countByUniteAndNumeroContainingAndFournisseurIdAndAppUserId(@Param("unite") String unite, @Param("numero") String numero,@Param("fournisseur_id") Long fournisseur_id, @Param("utilisateur_id") Long utilisateur_id);


    @Query("select COUNT(c) from Commande c inner join c.appUser u where c.id is null and c.supprime=false and c.valide=true and c.cloture = false and c.unite like %:unite% and c.reliquat-c.remise>0.01 and c.dateRelanceFacture < :date and u.id = :utilisateur_id")
    Long countCommandeNonFacturer(@Param("utilisateur_id") Long utilisateur_id, @Param("unite") String unite,@Param("date") Date date);

    @Query("select c from Commande c inner join c.appUser u where c.id is null and c.supprime=false and c.valide=true and c.cloture = false and c.unite like :unite and c.reliquat-c.remise>0.01 and c.dateRelanceFacture < :date and u.id = :utilisateur_id order by c.date desc")
    Page<Commande> selectCommandeNonFacturer(@Param("utilisateur_id") Long utilisateur_id, @Param("unite") String unite,@Param("date") Date date, Pageable pageable);

    @Query("select max(c.id) from Commande c where c.date = :year")
    Long getMaxIdPrecedant(@Param("year") int year);


    List<Commande> findByFournisseurId(Long id);

    @Query("SELECT c.numero as numero, c.date as date, c.unite as unite, c.modePaiment as modePaiment, c.delaiLivraison, c.delaiPaiment, c.lieuLivraison as lieuLivraison, fr.libelle as libelle, a.referenceArticle as referenceArticle, a.article, a.uniteMesure, a.quantite, a.prixUnitaire, (a.quantite * a.prixUnitaire) AS montant\n" +
            "FROM Commande c\n" +
            "INNER JOIN c.fournisseur fr\n" +
            "INNER JOIN c.commandeItems a\n" +
            "WHERE c.id = :id_commande")
    List<CommandeProjection> exportReport(@Param("id_commande") Long id_commande);

    Commande findCommandeById(Long id);







}
